package newsdaily.com.newsdaily;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import newsdaily.com.newsdaily.deps.DaggerDeps;
import newsdaily.com.newsdaily.deps.Deps;
import newsdaily.com.newsdaily.networking.NetworkModule;

import java.io.File;

/**
 * Created by ennur on 6/28/16.
 */
public class BaseApp extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}
