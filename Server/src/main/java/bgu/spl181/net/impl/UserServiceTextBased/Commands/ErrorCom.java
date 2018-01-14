package bgu.spl181.net.impl.UserServiceTextBased.Commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.BaseMessage;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.LinkedList;

public class ErrorCom extends BaseCommand {


    public ErrorCom(String _msg){
        this.msg=_msg;
    }
    @Override
    public String toStringCom() {
        return "ERROR "+msg;
    }

    @Override
    public BaseMessage Execute(Connections<BaseMessage> connections, int connId, LinkedList<User> users, boolean IsClienLogged) {
        return null;
    }
}