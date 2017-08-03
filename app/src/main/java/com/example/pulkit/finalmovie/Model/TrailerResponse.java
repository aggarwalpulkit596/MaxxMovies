package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 8/3/2017.
 */

public class TrailerResponse {

    @SerializedName("id")
    private int id_trailer;

    @SerializedName("results")
    private List<Trailers> results;

    public int getIdTrailer(){
        return id_trailer;
    }

    public void seIdTrailer(int id_trailer){
        this.id_trailer = id_trailer;
    }

    public List<Trailers> getResults(){
        return results;
    }

    public void setResults(List<Trailers> results) {
        this.results = results;
    }
    public List<Trailers> getMovies() {
        return results;
    }
    public void setMovies(List<Trailers> results) {
        this.results = results;
    }

}
