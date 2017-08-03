package com.example.pulkit.finalmovie.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class Movies implements Serializable {
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
    
}
