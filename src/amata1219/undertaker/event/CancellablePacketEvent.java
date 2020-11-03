package amata1219.undertaker.event;

import org.bukkit.entity.Player;

public class CancellablePacketEvent extends PacketEvent {

    public boolean isCancelled;

    public CancellablePacketEvent(Player player, Object packet) {
        super(player, packet);
    }

}
