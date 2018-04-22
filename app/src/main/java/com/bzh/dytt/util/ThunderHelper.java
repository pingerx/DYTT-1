package com.bzh.dytt.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

public class ThunderHelper {

    private static final String TAG = "ThunderHelper";

    private static final String XUNLEI_PACKAGENAME = "com.xunlei.downloadprovider";

    public boolean onClickDownload(Context context, String ftpUrl) {
        if (TextUtils.isEmpty(ftpUrl)) {
            return false;
        }
        if (checkIsInstall(context)) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getThunderEncode(ftpUrl))));
            return true;
        }
        return false;
    }

    private boolean checkIsInstall(Context paramContext) {
        try {
            paramContext.getPackageManager().getApplicationInfo(XUNLEI_PACKAGENAME, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, "checkIsInstall: ", e);
        }
        return false;
    }

    private String getThunderEncode(String ftpUrl) {
        return "thunder://" + XunLeiBase64.base64encode(("AA" + ftpUrl + "ZZ").getBytes());
    }
}
