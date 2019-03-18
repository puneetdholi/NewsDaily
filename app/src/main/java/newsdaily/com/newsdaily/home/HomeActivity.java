package newsdaily.com.newsdaily.home;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import newsdaily.com.newsdaily.BaseApp;
import newsdaily.com.newsdaily.R;
import newsdaily.com.newsdaily.connectivity.CheckNetwork;
import newsdaily.com.newsdaily.database.AppDatabase;
import newsdaily.com.newsdaily.database.DatabaseCallback;
import newsdaily.com.newsdaily.database.LocalCacheManager;

import newsdaily.com.newsdaily.models.Article;
import newsdaily.com.newsdaily.models.News;
import newsdaily.com.newsdaily.networking.Service;
import rx.Observable;
import rx.functions.Action1;


import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView ,DatabaseCallback {
    private static final String DB_NAME = "news_room_db";
    private RecyclerView list;
    private WebView webView;
    @Inject
    public Service service;
    ProgressBar progressBar;
    HomePresenter presenter;
    AppDatabase  database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(HomeActivity.this);

        renderView();
        init();
        networkCall();
    }

    private void networkCall() {

        Observable.just(CheckNetwork.isNetworkAvailable(this))
                .subscribeOn(rx.schedulers.Schedulers.newThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            presenter.getNewsList();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), R.string.no_internet, Snackbar.LENGTH_LONG)
                                    .show();
                            progressBar.setVisibility(View.GONE);

                             LocalCacheManager.getInstance(HomeActivity.this).getNews(HomeActivity.this);

                        }
                    }
                });
    }


    public void renderView() {
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.list);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress);
        webView.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        database = AppDatabase.getAppDatabase(HomeActivity.this);

    }

    public void init() {
        list.setLayoutManager(new LinearLayoutManager(this));
        presenter = new HomePresenter(service, this,HomeActivity.this);
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void onBackPressed() {
        if (webView.getVisibility() == View.VISIBLE) {
            webView.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void getNewsListSuccess(News newsListResponse) {

        LocalCacheManager.getInstance(HomeActivity.this).addNews(HomeActivity.this,newsListResponse );
        getNews();



    }



    private void getNews() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DB_NAME).build();
        db.newsDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<News>() {

            @Override
            public void accept(@io.reactivex.annotations.NonNull News news) throws Exception {
                if (news != null)
                    LocalCacheManager.getInstance(HomeActivity.this).getNews(HomeActivity.this);
            }

        });
    }


    public void setAdapter(News newsListResponse){

        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), newsListResponse.getArticles(),
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Article Item) {


                        list.setVisibility(View.GONE);


                        webView.setWebViewClient(new WebViewClient() {
                            private boolean flag = false;
                            String callbackUrl = getString(R.string.callback_url);

                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                if (!flag && url != null && url.startsWith(callbackUrl)) {
                                    // Check url whether Callback URL
                                    flag = true;
                                    // Clear cache, form, history
                                    webView.clearCache(true);
                                    webView.clearFormData();
                                    webView.clearHistory();
                                    CookieManager cmng = CookieManager.getInstance();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        cmng.removeAllCookies(value -> {
                                        });
                                    } else {
                                        cmng.removeAllCookie();
                                    }

                                    // Dismiss webview
                                    webView.setVisibility(WebView.GONE);
                                    webView.destroy();

                                }
                            }
                        });
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.loadUrl(Item.getUrl());
                        webView.setVisibility(WebView.VISIBLE);


                    }
                });

        list.setAdapter(adapter);

    }


    @Override
    public void onUsersLoaded(News newsListsResponse) {
        setAdapter(newsListsResponse);
    }

    @Override
    public void onUserDeleted() {

    }

    @Override
    public void onUserAdded() {

        Snackbar.make(findViewById(android.R.id.content), "Added", Snackbar.LENGTH_LONG)
                .show();

    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onUserUpdated() {

    }
}
