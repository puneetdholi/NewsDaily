package newsdaily.com.newsdaily.models;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public static String fromNewsList(List<Article> articleList) {
        if (articleList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Article>>() {}.getType();
        String json = gson.toJson(articleList, type);
        return json;
    }

    @TypeConverter
    public static List<Article> toNewsList(String articleString) {
        if (articleString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Article>>() {}.getType();
        List<Article> articleList = gson.fromJson(articleString, type);
        return articleList;
    }
}
