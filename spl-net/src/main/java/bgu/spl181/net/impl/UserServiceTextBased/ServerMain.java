package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.srv.Server;

import java.util.LinkedList;

public class ServerMain {


    public static void main(String[] args) {
        LinkedList<User> Users = new LinkedList<>();

        Server.threadPerClient(7777,
                () -> new USTBProtocol(Users),
                () -> new MessagingEncDecImpl()).serve();

        /*Server.reactor(
                5,
                7777,
                ()->new USTBProtocol(),
                ()->new MessagingEncDecImpl()).serve();
        */
    }


// you can use any server...
//        Server.threadPerClient(
//                7777, //port
//                () -> new RemoteCommandInvocationProtocol<>(feed), //protocol factory
//                ObjectEncoderDecoder::new //message encoder decoder factory
//        ).serve();
/*
        Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                7777, //port
                        () ->  new RemoteCommandInvocationProtocol<>(feed), //protocol factory
    ObjectEncoderDecoder::new //message encoder decoder factory
            ).serve();

}*/

}

