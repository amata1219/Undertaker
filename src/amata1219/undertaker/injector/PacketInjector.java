package amata1219.undertaker.injector;

import amata1219.undertaker.Undertaker;
import amata1219.undertaker.reflection.Reflect;
import io.netty.channel.ChannelPipeline;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class PacketInjector implements Listener {

    private final String identifier = "undertaker";
    private final PacketListenerRegistry registry;

    public PacketInjector(PacketListenerRegistry registry) {
        this.registry = registry;
    }

    @EventHandler
    public void onEnable(PluginEnableEvent event) {
        if (event.getPlugin().equals(Undertaker.instance()))
            for (Player player : Bukkit.getOnlinePlayers()) injectHandlerTo(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        injectHandlerTo(event.getPlayer());
    }

    private void injectHandlerTo(Player player) {
        ChannelPipeline pipeline = pipeline(player);
        if (pipeline.get(identifier) == null) pipeline.addBefore("packet_handler", identifier, new PacketHandler(registry, player));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        uninjectHandlerFrom(event.getPlayer());
    }

    private void uninjectHandlerFrom(Player player) {
        ChannelPipeline pipeline = pipeline(player);
        if (pipeline.get(identifier) != null) pipeline.remove(identifier);
    }

    private ChannelPipeline pipeline(Player player) {
        return Reflect.on(player)
                .call("getHandle")
                .field("playerConnection")
                .field("networkManager")
                .field("channel")
                .call("pipeline")
                .instance();
    }

}
