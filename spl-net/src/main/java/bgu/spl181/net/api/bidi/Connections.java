package bgu.spl181.net.api.bidi;

import bgu.spl181.net.srv.bidi.ConnectionHandler;

import java.util.Collection;
import java.util.HashMap;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void broadcast(T msg);

    void broadcastSpecific(T msg, Collection<Integer> specificConnection);

    int connect(ConnectionHandler<T> connectionHandler);

    void disconnect(int connectionId);

}