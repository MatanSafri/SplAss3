package bgu.spl181.net.impl.MovieRentalService.Requests;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.MovieRentalService.MovieUser;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.BaseMessage;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.RequestMes;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.LinkedList;

public class BalanceAddAmountReq extends RequestMes {

    private int amount;

    public BalanceAddAmountReq(int _amount){
        super("balance add amount");
        this.amount= _amount;
    }
    @Override
    public BaseMessage Execute(Connections<BaseMessage> connections, int connId,
                               LinkedList<User> users, boolean IsClienLogged) {
        return null;
    }
}
