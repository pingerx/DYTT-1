package com.bzh.dytt.colorhunt;

import android.util.Log;

import com.bzh.dytt.services.ColorHuntService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ColorHuntPresenter implements ColorHuntContract.Presenter {

    private static final String TAG = "ColorHuntPresenter";

    private ColorHuntContract.View mView;

    ColorHuntPresenter(ColorHuntContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://colorhunt.co/")
                .build();

        ColorHuntService service = retrofit.create(ColorHuntService.class);

        ColorHuntService.ColorHuntBody body = new ColorHuntService.ColorHuntBody(0, ColorHuntService.ColorHuntSort.Random, "");

        Call<String> call = service.listColor(RequestBody.create(MediaType.parse("text/plain"), body.toString()));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    @Override
    public void unsubscribe() {

    }
}
