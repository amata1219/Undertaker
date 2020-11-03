package amata1219.undertaker.injector;

import amata1219.undertaker.listener.PacketReceivedListener;
import amata1219.undertaker.listener.PacketReceivingListener;
import amata1219.undertaker.listener.PacketSendingListener;
import amata1219.undertaker.listener.PacketSentListener;

import java.util.ArrayList;
import java.util.List;

public class PacketListenerRegistry {

    final List<PacketSendingListener> sendingListeners = new ArrayList<>();
    final List<PacketReceivingListener> receivingListeners = new ArrayList<>();

    final List<PacketSentListener> sentListeners = new ArrayList<>();
    final List<PacketReceivedListener> receivedListeners = new ArrayList<>();

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

    public void register(PacketSentListener listener) {
        sentListeners.add(listener);
    }

    public void unregister(PacketSentListener listener) {
        sentListeners.remove(listener);
    }

    public void register(PacketReceivedListener listener) {
        receivedListeners.add(listener);
    }

    public void unregister(PacketReceivedListener listener) {
        receivedListeners.remove(listener);
    }

}
