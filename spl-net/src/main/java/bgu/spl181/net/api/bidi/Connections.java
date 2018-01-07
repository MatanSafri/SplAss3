package bgu.spl181.net.api.bidi;

import bgu.spl181.net.srv.ConnectionHandler;

import java.io.IOException;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void broadcast(T msg);

    void disconnect(int connectionId);

    int connect(ConnectionHandler<T> connectionHandler);

}