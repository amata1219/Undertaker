package amata1219.undertaker.listener;

import amata1219.undertaker.event.CancellablePacketEvent;

public interface PacketReceivingListener {

    void onReceiving(CancellablePacketEvent event);

}
