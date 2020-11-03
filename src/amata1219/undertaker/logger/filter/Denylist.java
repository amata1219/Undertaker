package amata1219.undertaker.logger.filter;

import java.util.Set;

public class Denylist implements PacketFilter {

    private final Set<String> deniedPacketNames;

    public Denylist(Set<String> deniedPacketNames) {
        this.deniedPacketNames = deniedPacketNames;
    }

    @Override
    public boolean shouldIgnore(String packetName) {
        return deniedPacketNames.contains(packetName);
    }
}
