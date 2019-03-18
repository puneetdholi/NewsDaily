package newsdaily.com.newsdaily.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import newsdaily.com.newsdaily.models.News;

public class Converters {

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
