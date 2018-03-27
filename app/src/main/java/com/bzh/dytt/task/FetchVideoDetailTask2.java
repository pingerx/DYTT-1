package com.bzh.dytt.task;


import android.text.TextUtils;
import android.util.Log;

import com.bzh.dytt.data.CategoryMap;
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.db.AppDatabase;
import com.bzh.dytt.data.network.ApiResponse;
import com.bzh.dytt.data.network.DyttService;
import com.bzh.dytt.util.VideoDetailPageParser;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class FetchVideoDetailTask2 implements Runnable {

    private static final String TAG = "FetchVideoDetailTask";

    private final List<CategoryMap> mCategoryMap;
    private final DyttService mService;
    private VideoDetailPageParser mParser;
    private AppDatabase mDatabase;

    public FetchVideoDetailTask2(List<CategoryMap> categoryMap, AppDatabase database, DyttService service, VideoDetailPageParser parser) {
        mCategoryMap = categoryMap;
        mService = service;
        mParser = parser;
        mDatabase = database;
    }


    @Override
    public void run() {
        try {
            mDatabase.beginTransaction();
            for (CategoryMap category : mCategoryMap) {
                boolean isParsed = mDatabase.categoryMapDAO().IsParsed(category.getLink());
                if (isParsed) {
                    continue;
                }
                Response<ResponseBody> response = mService.getVideoDetailNew(category.getLink()).execute();
                ApiResponse<ResponseBody> apiResponse = new ApiResponse<>(response);

                if (apiResponse.isSuccessful()) {
                    VideoDetail videoDetail = mParser.parseVideoDetail(new String(apiResponse.body.bytes(), "GB2312"));
                    if (TextUtils.isEmpty(videoDetail.getName())) {
                        videoDetail.setValidVideoItem(false);
                    } else {
                        videoDetail.setValidVideoItem(true);
                    }
                    videoDetail.setDetailLink(category.getLink());
                    videoDetail.setSN(category.getSN());
                    mDatabase.videoDetailDAO().updateVideoDetail(videoDetail);
                    category.setIsParsed(true);
                    mDatabase.categoryMapDAO().updateCategory(category);
                }
            }
            mDatabase.setTransactionSuccessful();
        } catch (IOException e) {
            Log.e("FetchVideoDetailTask", "Something wrong when fetch video detail " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
    }
}
