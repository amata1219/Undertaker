package amata1219.undertaker.injector;

import amata1219.undertaker.event.CancellablePacketEvent;
import amata1219.undertaker.event.PacketEvent;
import amata1219.undertaker.listener.PacketReceivedListener;
import amata1219.undertaker.listener.PacketReceivingListener;
import amata1219.undertaker.listener.PacketSendingListener;
import amata1219.undertaker.listener.PacketSentListener;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;

import java.util.List;

public class PacketHandler extends ChannelDuplexHandler {

    private final List<PacketSendingListener> sendingListeners;
    private final List<PacketReceivingListener> receivingListeners;
    private final List<PacketSentListener> sentListeners;
    private final List<PacketReceivedListener> receivedListeners;
    private final Player player;

    public PacketHandler(PacketListenerRegistry registry, Player player) {
        this.sendingListeners = registry.sendingListeners;
        this.receivingListeners = registry.receivingListeners;
        this.sentListeners = registry.sentListeners;
        this.receivedListeners = registry.receivedListeners;
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        CancellablePacketEvent event = new CancellablePacketEvent(player, msg);
        for (PacketSendingListener listener : sendingListeners) listener.onSending(event);
        if (event.isCancelled) return;
        super.write(ctx, msg, promise);
        for (PacketSentListener listener : sentListeners) listener.onSent(event);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CancellablePacketEvent event = new CancellablePacketEvent(player, msg);
        for (PacketReceivingListener listener : receivingListeners) listener.onReceiving(event);
        if (event.isCancelled) return;
        super.channelRead(ctx, msg);
        for (PacketReceivedListener listener : receivedListeners) listener.onReceived(event);
    }
}
