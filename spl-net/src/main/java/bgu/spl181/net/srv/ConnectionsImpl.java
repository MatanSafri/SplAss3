package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.srv.bidi.ConnectionHandler;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ConnectionsImpl<T> implements Connections<T> {

    public   WeakHashMap<ConnectionHandler<T>,Integer> _connectionHandlers = new WeakHashMap<>();
    private static AtomicInteger _idCounter = new AtomicInteger(0);

    private ConnectionHandler<T> getConnectionById(int connectionId)
    {

        /*Iterator<Map.Entry<ConnectionHandler<T>, Integer>> it = _connectionHandlers.entrySet().iterator();
        if(it.hasNext()) {
            Map.Entry<ConnectionHandler<T>, Integer> item = it.next();
            if (item.getValue() == connectionId) {
                return item.getKey();
            }
        }
        return null;*/

        for (Map.Entry<ConnectionHandler<T>, Integer> entry : _connectionHandlers.entrySet()) {
            if (entry.getValue() == connectionId) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean send(int connectionId, T msg)
    {
        ConnectionHandler connectionHandler = getConnectionById(connectionId);

        if (connectionHandler == null)
            return false;

        try {
            System.out.println("client " + connectionId + " sending message: " + msg);
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
                _connectionHandlers.keySet()) {
            connectionHandler.send(msg);
        }
    }

    @Override
    public void broadcastSpecific(T msg, Collection<Integer> specificConnection) {
        for(Integer connectionId:
                specificConnection)
        {
            ConnectionHandler<T> handler = getConnectionById(connectionId);
            if (handler != null) {
                System.out.println("client " + connectionId + " sending message: " + msg);
                handler.send(msg);
            }

        }
    }


    public int connect(ConnectionHandler<T> connectionHandler) {
        _connectionHandlers.put(connectionHandler,_idCounter.incrementAndGet());
        return _idCounter.get();
    }

}