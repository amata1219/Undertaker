package amata1219.undertaker.logger.filter;

public interface PacketFilter {

    boolean shouldAllow(String packetName);

}
