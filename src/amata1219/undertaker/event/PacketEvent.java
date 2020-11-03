package amata1219.undertaker.event;

import org.bukkit.entity.Player;

public class PacketEvent {

    public final Player player;
    public final String packetName;
    public final Object packet;

    public PacketEvent(Player player, Object packet) {
        this.player = player;
        this.packetName = packet.getClass().getSimpleName();
        this.packet = packet;
    }

}
