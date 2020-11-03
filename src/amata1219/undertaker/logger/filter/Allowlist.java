package amata1219.undertaker.logger.filter;

import java.util.Set;

public class Allowlist implements PacketFilter {

    private final Set<String> allowedPacketNames;

    public Allowlist(Set<String> allowedPacketNames) {
        this.allowedPacketNames = allowedPacketNames;
    }

    @Override
    public boolean shouldIgnore(String packetName) {
        return !allowedPacketNames.contains(packetName);
    }
}
