package amata1219.undertaker;

import amata1219.undertaker.injector.PacketInjector;
import amata1219.undertaker.injector.PacketListenerRegistry;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Undertaker extends JavaPlugin implements UndertakerAPI {

    private static Undertaker instance;
    private final PacketListenerRegistry registry = new PacketListenerRegistry();

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new PacketInjector(registry), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public static Undertaker instance() {
        return instance;
    }

    @Override
    public PacketListenerRegistry registry() {
        return registry;
    }
}
