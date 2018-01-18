package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.srv.bidi.ConnectionHandler;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class ConnectionsImpl<T> implements Connections<T> {

    public ConcurrentHashMap<Integer,ConnectionHandler<T>> _connectionHandlers = new ConcurrentHashMap<>();
    private static AtomicInteger _idCounter = new AtomicInteger(0);

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
            ConnectionHandler<T> handler = _connectionHandlers.get(connectionId);
            if (handler != null)

                handler.send(msg);

        }
    }




    public int connect(ConnectionHandler<T> connectionHandler) {
        _connectionHandlers.put(_idCounter.incrementAndGet(),connectionHandler);
        return _idCounter.get();
    }

    @Override
    public void disconnect(int connectionId) {
        _connectionHandlers.remove(connectionId);
    }

}