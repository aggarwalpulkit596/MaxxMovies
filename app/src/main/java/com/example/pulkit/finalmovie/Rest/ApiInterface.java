package com.example.pulkit.finalmovie.Rest;

import com.example.pulkit.finalmovie.Model.AccountResponse;
import com.example.pulkit.finalmovie.Model.CreditResponse;
import com.example.pulkit.finalmovie.Model.LoginResponse;
import com.example.pulkit.finalmovie.Model.MovieResponse;
import com.example.pulkit.finalmovie.Model.PostResponse;
import com.example.pulkit.finalmovie.Model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pulkit on 8/2/2017.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apikey, @Query("page") int pageIndex);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apikey, @Query("page") int pageIndex);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apikey, @Query("page") int pageIndex);

    @GET("movie/popular")
    Call<MovieResponse> getPopularovies(@Query("api_key") String apikey, @Query("page") int pageIndex);

    @GET("tv/popular")
    Call<MovieResponse> getPopularSeries(@Query("api_key") String apikey, @Query("page") int pageIndex);

    @GET("tv/top_rated")
    Call<MovieResponse> getTopRatedSeries(@Query("api_key") String apikey, @Query("page") int pageIndex);

    @GET("tv/on_the_air")
    Call<MovieResponse> getOnTheAirSeries(@Query("api_key") String apikey, @Query("page") int pageIndex);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("tv/{movie_id}/videos")
    Call<TrailerResponse> getSeriesTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<CreditResponse> getMovieCredits(@Path("id") int id, @Query("api_key") String apikey);

    @GET("tv/{tv_id}/credits")
    Call<CreditResponse> getSeriesCredits(@Path("tv_id") int id, @Query("api_key") String apikey);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("api_key") String apikey, @Query("query") String search);

    @GET("movie/{movie_id}/recommendations")
    Call<MovieResponse> getMovieRecommendations(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/recommendations")
    Call<MovieResponse> getSeriesRecommendations(@Path("tv_id") int id, @Query("api_key") String apiKey);

    @GET("authentication/token/new")
    Call<LoginResponse> getRequestToken(@Query("api_key") String apiKey);

    @GET("authentication/session/new")
    Call<LoginResponse> getSessionId(@Query("api_key") String apiKey, @Query("request_token") String token);

    @GET("authentication/token/validate_with_login")
    Call<LoginResponse> getLogin(@Query("api_key") String apiKey, @Query("username") String username, @Query("password") String password, @Query("request_token") String token);

    @POST("account/{account_id}/favorite")
    @FormUrlEncoded
    Call<PostResponse> markFav(@Path("account_id") int id, @Query("api_key") String apiKey, @Query("session_id") String username, @Field("media_type") String type, @Field("media_id") int mediaid, @Field("favorite") boolean add);

    @POST("account/{account_id}/watchlist")
    @FormUrlEncoded
    Call<PostResponse> markWish(@Path("account_id") int id, @Query("api_key") String apiKey, @Query("session_id") String username, @Field("media_type") String type, @Field("media_id") int mediaid, @Field("watchlist") boolean add);

    @GET("account")
    Call<AccountResponse> getDetail(@Query("api_key") String apiKey, @Query("session_id") String username);

    @GET("account/{account_id}/watchlist/movies")
    Call<MovieResponse> getMovieWish(@Path("account_id") int id, @Query("api_key") String apiKey, @Query("session_id") String username, @Query("page") int pageIndex);

    @GET("account/{account_id}/watchlist/tv")
    Call<MovieResponse> getTvWish(@Path("account_id") int id, @Query("api_key") String apiKey, @Query("session_id") String username, @Query("page") int pageIndex);


}
