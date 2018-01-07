package bgu.spl181.net.impl.UserServiceTextBased.Commands;

import bgu.spl181.net.impl.UserServiceTextBased.Messages.BaseMessage;
import bgu.spl181.net.srv.BaseServer;

public abstract class BaseCommand extends BaseMessage {

    String msg;

    public abstract String toStringCom ();

}
