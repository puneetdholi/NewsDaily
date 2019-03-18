package newsdaily.com.newsdaily.networking;


import newsdaily.com.newsdaily.models.News;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ennur on 6/25/16.
 */
public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getNewsList(final GetNewsListCallback callback) {

        return networkService.getNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io(), true)
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends News>>() {
                    @Override
                    public Observable<? extends News> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(News newsListResponse) {
                        callback.onSuccess(newsListResponse);

                    }
                });
    }

    public interface GetNewsListCallback{
        void onSuccess(News newsListResponse);

        void onError(NetworkError networkError);
    }
}
