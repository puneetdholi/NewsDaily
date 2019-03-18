package newsdaily.com.newsdaily.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import newsdaily.com.newsdaily.models.Article;
import newsdaily.com.newsdaily.models.News;

@Entity(tableName = "news")
public class NewsDb {

    @PrimaryKey(autoGenerate = true)
    private int uid;


    private String status;


    private Integer totalResults;

    @TypeConverters(DataConverter.class)
    private List<Article> articles = null;


    public List<Article> getArticleList() {
        return articles;
    }


    public NewsDb(String status, Integer totalResults ){
        this.status = status;
        this.totalResults = totalResults;
      //  this.articles=articles;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }


}


class DataConverter {

    @TypeConverter
    public String fromNewsList(List<News> newsList) {
        if (newsList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<News>>() {}.getType();
        String json = gson.toJson(newsList, type);
        return json;
    }

    @TypeConverter
    public List<News> toNewsList(String newsString) {
        if (newsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<News>>() {}.getType();
        List<News> newsList = gson.fromJson(newsString, type);
        return newsList;
    }
}


