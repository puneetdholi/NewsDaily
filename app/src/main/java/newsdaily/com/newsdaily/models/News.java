
package newsdaily.com.newsdaily.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import newsdaily.com.newsdaily.database.Converters;

@Entity
public class News implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;


    @SerializedName("articles")
    @Expose
    @TypeConverters(DataConverter.class)
    private List<Article> articles = null;


    public News(String status, int totalResults, List<Article> articles){
        this.status = status;
        this.totalResults = totalResults;
        this.articles= articles;
    };

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

    public List<Article> getArticles() {
        return articles;
    }


    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}

