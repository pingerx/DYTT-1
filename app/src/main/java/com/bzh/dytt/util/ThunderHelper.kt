package com.bzh.dytt.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

class ThunderHelper {

    fun onClickDownload(context: Context?, ftpUrl: String?) = if (ftpUrl != null && !ftpUrl.isEmpty() && checkIsInstall(context)) {
        val url = ftpUrl.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        context?.startActivity(Intent("android.intent.action.VIEW", Uri.parse(url[0])))
        true
    } else {
        false
    }

    private fun checkIsInstall(context: Context?) = try {
        context?.packageManager?.getApplicationInfo(XUN_LEI_PACKAGE_NAME, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
//        Logger.e(TAG, "checkIsInstall: ", e)
        false
    }

    companion object {

        private const val TAG = "ThunderHelper"

        private const val XUN_LEI_PACKAGE_NAME = "com.xunlei.downloadprovider"
    }
}


