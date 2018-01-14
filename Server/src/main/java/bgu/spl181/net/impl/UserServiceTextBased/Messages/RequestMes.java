package bgu.spl181.net.impl.UserServiceTextBased.Messages;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.AckCom;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.ErrorCom;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.LinkedList;
import java.util.Map;

public abstract class RequestMes extends BaseMessage {

    protected String ServiceName;

    public RequestMes(String _ServiceName){
        this.name = "Request";
        this.ServiceName=_ServiceName;
    }

    public abstract BaseMessage Execute(Connections<BaseMessage> connections, int connId,
                                        Map<String,User> users, boolean IsClienLogged);


}
