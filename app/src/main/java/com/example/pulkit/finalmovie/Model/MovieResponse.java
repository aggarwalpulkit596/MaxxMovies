package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<Movies> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Movies> getResults() {
        return results;
    }

    public List<Movies> getMovies() {
        return results;
    }

    public void setResults(ArrayList<Movies> results) {
        this.results = results;
    }

    public void setMovies(ArrayList<Movies> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
