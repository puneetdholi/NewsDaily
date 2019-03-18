package newsdaily.com.newsdaily.database;

import java.util.List;

import newsdaily.com.newsdaily.models.News;

public interface DatabaseCallback {

    void onUsersLoaded(News users);

    void onUserDeleted();

    void onUserAdded();

    void onDataNotAvailable();

    void onUserUpdated();
}
