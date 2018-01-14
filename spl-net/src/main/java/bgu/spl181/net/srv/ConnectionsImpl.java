package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.srv.bidi.ConnectionHandler;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ConnectionsImpl<T> implements Connections<T> {

    public   HashMap<Integer,ConnectionHandler<T>> _connectionHandlers = new HashMap<>();
    private static AtomicInteger _idCounter = new AtomicInteger(0);


    public HashMap<Integer, ConnectionHandler<T>> getConnectionHandlers() {
        return _connectionHandlers;
    }

    public boolean send(int connectionId, T msg)
    {
        ConnectionHandler connectionHandler = _connectionHandlers.get(connectionId);

        if (connectionHandler == null)
            return false;

        try {
            connectionHandler.send(msg);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void broadcast(T msg)
    {
        for ( ConnectionHandler connectionHandler:
                _connectionHandlers.values()) {
            connectionHandler.send(msg);
        }
    }

    @Override
    public void broadcastSpecific(T msg, Collection<Integer> specificConnection) {
        for(Integer connectionId:
                specificConnection)
        {
            if (_connectionHandlers.containsKey(connectionId))
                _connectionHandlers.get(connectionId).send(msg);

        }
    }

    public void disconnect(int connectionId)
    {
        try {
            _connectionHandlers.get(connectionId).close();
            _connectionHandlers.remove(connectionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int connect(ConnectionHandler<T> connectionHandler) {
        _connectionHandlers.put(_idCounter.get(),connectionHandler);
        return _idCounter.getAndIncrement();
    }

}