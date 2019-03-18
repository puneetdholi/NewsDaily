package newsdaily.com.newsdaily.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import newsdaily.com.newsdaily.models.News;

public class LocalCacheManager {
    private static final String DB_NAME = "news_room_db";
    private Context context;
    private static LocalCacheManager _instance;
    private AppDatabase db;



    public static LocalCacheManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new LocalCacheManager(context);
        }
        return _instance;
    }

    public LocalCacheManager(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public void getNews(final DatabaseCallback databaseCallback) {
        db.newsDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<News>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull News news) throws Exception {
                databaseCallback.onUsersLoaded(news);

            }
        });
    }

    public void addNews(final DatabaseCallback databaseCallback, News insertNews) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.newsDao().insertAll(insertNews);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                databaseCallback.onUserAdded();
            }

            @Override
            public void onError(Throwable e) {
                databaseCallback.onDataNotAvailable();
            }
        });
    }

    public void deleteNews(final DatabaseCallback databaseCallback, final News news) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.newsDao().delete(news);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                databaseCallback.onUserDeleted();
            }

            @Override
            public void onError(Throwable e) {
                databaseCallback.onDataNotAvailable();
            }
        });
    }


    public void updateNews(final DatabaseCallback databaseCallback, final News news) {
        news.setStatus("first name first name");
        news.setTotalResults(1);
        //news.setArticles(null);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.newsDao().updateNews(news);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                databaseCallback.onUserUpdated();
            }

            @Override
            public void onError(Throwable e) {
                databaseCallback.onDataNotAvailable();
            }
        });
    }
}
