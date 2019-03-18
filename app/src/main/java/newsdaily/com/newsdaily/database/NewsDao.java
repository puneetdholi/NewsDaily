package newsdaily.com.newsdaily.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Maybe;
import newsdaily.com.newsdaily.models.News;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    Maybe<News> getAll();

    @Insert
    void insertAll(News news);

    @Delete
    void delete(News news);

    @Update
    public void updateNews(News news);
}
