package amata1219.undertaker.injector;

import amata1219.undertaker.listener.PacketReceivingListener;
import amata1219.undertaker.listener.PacketSendingListener;

import java.util.ArrayList;
import java.util.List;

public class PacketListenerRegistry {

    final List<PacketSendingListener> sendingListeners = new ArrayList<>();
    final List<PacketReceivingListener> receivingListeners = new ArrayList<>();

    public void register(PacketSendingListener listener) {
        sendingListeners.add(listener);
    }

    public void unregister(PacketSendingListener listener) {
        sendingListeners.remove(listener);
    }

    public void register(PacketReceivingListener listener) {
        receivingListeners.add(listener);
    }

    public void unregister(PacketReceivingListener listener) {
        receivingListeners.remove(listener);
    }

}
