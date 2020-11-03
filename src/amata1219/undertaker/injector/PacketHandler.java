package amata1219.undertaker.injector;

import amata1219.undertaker.event.PacketEvent;
import amata1219.undertaker.listener.PacketReceivingListener;
import amata1219.undertaker.listener.PacketSendingListener;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;

import java.util.List;

public class PacketHandler extends ChannelDuplexHandler {

    private final List<PacketSendingListener> sendingListeners;
    private final List<PacketReceivingListener> receivingListeners;
    private final Player player;

    public PacketHandler(PacketListenerRegistry registry, Player player) {
        this.sendingListeners = registry.sendingListeners;
        this.receivingListeners = registry.receivingListeners;
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        PacketEvent event = new PacketEvent(player, msg);
        for (PacketSendingListener listener : sendingListeners) listener.onSending(event);
        if (!event.isCancelled) super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketEvent event = new PacketEvent(player, msg);
        for (PacketReceivingListener listener : receivingListeners) listener.onReceiving(event);
        if (!event.isCancelled) super.channelRead(ctx, msg);
    }
}
