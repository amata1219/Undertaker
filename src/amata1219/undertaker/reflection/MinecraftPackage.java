package amata1219.undertaker.reflection;

import org.bukkit.Bukkit;

public enum MinecraftPackage {

    NET_MINECRAFT_SERVER("net.minecraft.server"),
    ORG_BUKKIT_CRAFTBUKKIT("org.bukkit.craftbukkit");

    private final String path;

    MinecraftPackage(String prefix) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replaceFirst(".*(\\\\d+_\\\\d+_R\\\\d+).*", "$1");
        this.path = prefix + ".v" + version;
    }

    @Override
    public String toString() {
        return path;
    }

}
