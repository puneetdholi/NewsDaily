package newsdaily.com.newsdaily.networking;


import newsdaily.com.newsdaily.models.News;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    @GET("top-headlines?country=us&apiKey=a56e32085c0543c383222549a3f9a86d")
    Observable<News> getNewsList();

}
