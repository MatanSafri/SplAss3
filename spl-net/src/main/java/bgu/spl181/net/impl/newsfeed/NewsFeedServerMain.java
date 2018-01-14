package bgu.spl181.net.impl.newsfeed;

import bgu.spl181.net.api.MessageEncoderDecoder;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.impl.MovieRentalService.DB.MovieRentalDbExecutor;
import bgu.spl181.net.impl.MovieRentalService.DB.MoviesDB;
import bgu.spl181.net.impl.MovieRentalService.DB.UsersDB;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.Movie;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.MovieUser;
import bgu.spl181.net.impl.MovieRentalService.MovieRentalServiceProtocol;
import bgu.spl181.net.impl.MovieRentalService.MovieRentalSharedData;
import bgu.spl181.net.impl.MovieRentalService.MoviesRentalDataExecutor;
import bgu.spl181.net.impl.UserServiceTextBased.MessagingEncDecImpl;
import bgu.spl181.net.srv.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class NewsFeedServerMain {

    public static void main(String[] args) {
        NewsFeed feed = new NewsFeed(); //one shared object


        Map<String,Movie> movies = MoviesDB.getInstance().getData();
        Map<String,MovieUser> users = UsersDB.getInstance().getData();

        MoviesRentalDataExecutor moviesRentalDataExecutor = new MovieRentalDbExecutor(
                MoviesDB.getInstance(),
                UsersDB.getInstance()
        );

        MovieRentalSharedData moviesSharedData = new MovieRentalSharedData(
                users,
                movies,
            moviesRentalDataExecutor
        );

        Supplier<BidiMessagingProtocol<String>> protocolSupplier  =
                () -> new MovieRentalServiceProtocol(moviesSharedData
        );
        Supplier<MessageEncoderDecoder<String>> messageEncoderDecoderSupplier =
                () -> new MessagingEncDecImpl();


        Server.threadPerClient(
                7777,
                protocolSupplier,
                messageEncoderDecoderSupplier
        ).serve();

         /*Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                7777, //port
                 protocolSupplier,
                 messageEncoderDecoderSupplier
         ).serve();

// you can use any server... 
//        Server.threadPerClient(
//                7777, //port
//                () -> new RemoteCommandInvocationProtocol<>(feed), //protocol factory
//                ObjectEncoderDecoder::new //message encoder decoder factory
//        ).serve();

       /* Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                7777, //port
                () ->  new RemoteCommandInvocationProtocol<>(feed), //protocol factory
                ObjectEncoderDecoder::new //message encoder decoder factory
        ).serve();*/

    }
}
