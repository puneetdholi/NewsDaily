package newsdaily.com.newsdaily.database;


import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import newsdaily.com.newsdaily.models.News;

@Database(entities = {News.class}, version = 2)
    public abstract class AppDatabase extends RoomDatabase {


     private static final String DATABASE_NAME = "news_room_db";

     public abstract NewsDao newsDao();


     private static AppDatabase INSTANCE;

     public static AppDatabase getAppDatabase(Activity context) {
         if (INSTANCE == null) {
             INSTANCE =
                     Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                             // allow queries on the main thread.
                             // Don't do this on a real app! See PersistenceBasicSample for an example.
                            // .fallbackToDestructiveMigration()
                             .build();
         }
         return INSTANCE;
     }

     public static void destroyInstance() {
         INSTANCE = null;
     }

}
