package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.BaseCommand;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.ErrorCom;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.BaseMessage;
import bgu.spl181.net.srv.ConnectionHandler;

import java.util.LinkedList;

public class USTBProtocol<BaseMassage> implements BidiMessagingProtocol <BaseMessage> {

    private Connections<BaseMessage> connections;
    private int connectionId;
    private boolean _shouldTerminate = false;
    private LinkedList<User> users;
    private boolean isClientLoggedIn = false;

    public USTBProtocol(LinkedList<User> _users){
        this.users=_users;
    }

    public void start(int connectionId, Connections<BaseMessage> connections){
        this.connectionId = connectionId;
        this.connections = connections;
    }

    public void process(BaseMessage message){

        BaseMessage response = message.Execute(connections, connectionId,users,isClientLoggedIn);

        if(message.getName().equals("Signout") && message.getResponse()) {
            _shouldTerminate = true;
        }
        else if (message.getName()=="Login" && message.getResponse())
                isClientLoggedIn = true;
            //System.out.println(message); //maybe don't need this

        connections.send(connectionId, response);
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return _shouldTerminate;
    }

}
