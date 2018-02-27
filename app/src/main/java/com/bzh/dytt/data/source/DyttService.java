package com.bzh.dytt.data.source;

import android.arch.lifecycle.LiveData;

import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface DyttService {

    @GET("/")
    LiveData<ApiResponse<ResponseBody>> getHomePage();
}
