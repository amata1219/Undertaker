package amata1219.undertaker.logger;

import amata1219.undertaker.event.PacketEvent;
import amata1219.undertaker.listener.PacketReceivingListener;
import amata1219.undertaker.listener.PacketSendingListener;
import amata1219.undertaker.logger.filter.Denylist;
import amata1219.undertaker.logger.filter.PacketFilter;

import java.util.Collections;

public class PacketLogger implements PacketSendingListener, PacketReceivingListener {

    private final PacketFilter packetSendingFilter, packetReceivingFilter;

    public PacketLogger() {
        PacketFilter filter = new Denylist(Collections.emptySet());
        this.packetSendingFilter = filter;
        this.packetReceivingFilter = filter;
    }

    public PacketLogger(PacketFilter packetSendingFilter, PacketFilter packetReceivingFilter) {
        this.packetSendingFilter = packetSendingFilter;
        this.packetReceivingFilter = packetReceivingFilter;
    }

    @Override
    public void onSending(PacketEvent event) {
        if (!packetSendingFilter.shouldIgnore(event.packetName)) log(event, PacketType.SENDING);
    }

    @Override
    public void onReceiving(PacketEvent event) {
        if (!packetReceivingFilter.shouldIgnore(event.packetName)) log(event, PacketType.RECEIVING);
    }

    private void log(PacketEvent event, PacketType type) {
        System.out.println(type.toString() + ": " + event.packetName + " -> " + event.player.getName());
    }

    private enum PacketType {

        SENDING,
        RECEIVING;

    }

}
