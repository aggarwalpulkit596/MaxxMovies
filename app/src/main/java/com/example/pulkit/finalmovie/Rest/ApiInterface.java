package com.example.pulkit.finalmovie.Rest;

import com.example.pulkit.finalmovie.Model.MovieResponse;
import com.example.pulkit.finalmovie.Model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pulkit on 8/2/2017.
 */

public interface ApiInterface {    @GET("movie/top_rated")
Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apikey);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apikey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apikey);

    @GET("movie/popular")
    Call<MovieResponse> getPopularovies(@Query("api_key") String apikey);

    @GET("tv/popular")
    Call<MovieResponse> getPopularSeries(@Query("api_key") String apikey);

    @GET("tv/top_rated")
    Call<MovieResponse> getTopRatedSeries(@Query("api_key") String apikey);

    @GET("tv/on_the_air")
    Call<MovieResponse> getOnTheAirSeries(@Query("api_key") String apikey);
    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

}
