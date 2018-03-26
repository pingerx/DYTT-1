package com.bzh.dytt.data.network;

import android.arch.lifecycle.LiveData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface DyttService {

    @GET("/")
    LiveData<ApiResponse<ResponseBody>> getHomePage();

    @GET
    LiveData<ApiResponse<ResponseBody>> getHomePage2(@Url String url);

    @GET("/{detail_link}")
    LiveData<ApiResponse<ResponseBody>> getVideoDetail(@Path("detail_link") String detailLink);

    @GET("/{detail_link}")
    Call<ResponseBody> getVideoDetailNew(@Path("detail_link") String detailLink);

    @GET("/html/gndy/{category_string}")
    LiveData<ApiResponse<ResponseBody>> getMovieListByCategory(@Path("category_string") String category);

}
