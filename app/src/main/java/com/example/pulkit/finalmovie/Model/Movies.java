package com.example.pulkit.finalmovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class Movies implements Parcelable {
    @SerializedName("first_air_date")
    private String firstairdate;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("id")
    private Integer id;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("name")
    private String name;

    public Movies(String firstairdate, String title, String poster, String description, String backdrop, Boolean adult, Integer id, String releaseDate, List<Integer> genreIds, Double popularity, Integer voteCount, Double voteAverage, String name) {
        this.firstairdate = firstairdate;
        this.title = title;
        this.poster = poster;
        this.description = description;
        this.backdrop = backdrop;
        this.adult = adult;
        this.id = id;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.name = name;
    }

    protected Movies(Parcel in) {
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
        releaseDate = in.readString();
        name = in.readString();
        id = in.readInt();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        voteCount = in.readInt();
        firstairdate = in.readString();
//        List<Integer>genres = new ArrayList<>();
//        genreIds=in.readList(genres,null);

    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };


    public String getFirstairdate() {
        return firstairdate;
    }

    public void setFirstairdate(String firstairdate) {
        this.firstairdate = firstairdate;
    }

    public String getName() {
        return (name == null) ? title : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return (title == null) ? name : title;//return name in case of tv and title in case of movies
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return "http://image.tmdb.org/t/p/w500" + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return (releaseDate == null) ? firstairdate : releaseDate;//return name in case of tv and title in case of movies
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(backdrop);
        parcel.writeDouble(voteAverage);
        parcel.writeString(name);
        parcel.writeList(genreIds);
        parcel.writeInt(id);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);
        parcel.writeInt(voteCount);
        parcel.writeDouble(voteAverage);
        parcel.writeString(firstairdate);

    }
}
