package newsdaily.com.newsdaily.home;

import newsdaily.com.newsdaily.models.News;

/**
 * Created by ennur on 6/25/16.
 */
public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getNewsListSuccess(News newsListResponse);

}
