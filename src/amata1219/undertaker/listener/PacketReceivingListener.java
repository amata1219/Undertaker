package amata1219.undertaker.listener;

import amata1219.undertaker.event.PacketEvent;

public interface PacketReceivingListener {

    void onReceiving(PacketEvent event);

}
