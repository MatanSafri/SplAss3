package bgu.spl181.net.impl.MovieRentalService.DB;


import java.util.List;
import java.util.Map;

public interface DataCommands<K,T> {
    boolean saveData(Map<K,T> data);
    Map<K,T> getData();
}
