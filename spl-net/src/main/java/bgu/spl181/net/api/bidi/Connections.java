package bgu.spl181.net.api.bidi;

import bgu.spl181.net.srv.ConnectionHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void broadcast(T msg);

    void broadcastSpecific(T msg, Collection<Integer> specificConnection);

    void disconnect(int connectionId);

    int connect(ConnectionHandler<T> connectionHandler);

    HashMap<Integer,ConnectionHandler<T>> getConnectionHandlers();

}