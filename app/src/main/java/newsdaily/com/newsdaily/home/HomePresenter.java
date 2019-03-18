package newsdaily.com.newsdaily.home;

import android.app.Activity;

import java.util.List;

import newsdaily.com.newsdaily.models.News;
import newsdaily.com.newsdaily.networking.NetworkError;
import newsdaily.com.newsdaily.networking.Service;

import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ennur on 6/25/16.
 */
public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;
    Activity activity;


    public HomePresenter(Service service, HomeView view, HomeActivity homeActivity) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        this.activity = homeActivity;
    }

    public void getNewsList() {
        view.showWait();

        Subscription subscription = service.getNewsList(new Service.GetNewsListCallback() {
            @Override
            public void onSuccess(News newsListResponse) {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        view.removeWait();
                        view.getNewsListSuccess(newsListResponse);

                    }
                });

            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }


}
