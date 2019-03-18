package newsdaily.com.newsdaily.deps;


import newsdaily.com.newsdaily.home.HomeActivity;
import newsdaily.com.newsdaily.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ennur on 6/28/16.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
