package bgu.spl181.net.impl.BBtpc;

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

import java.util.Map;
import java.util.function.Supplier;

public class TPCMain {

    public static void main(String[] args) {

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
    }
}
