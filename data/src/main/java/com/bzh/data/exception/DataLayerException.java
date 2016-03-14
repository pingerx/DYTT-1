package com.bzh.data.exception;

import android.support.annotation.StringDef;
import android.text.TextUtils;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-14<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DataLayerException extends Exception {

    @StringDef({ERROR_NONE_NETWORK, ERROR_TIMEOUT, ERROR_RESULT_ILLEGAL})
    public @interface DATA_LAYER_ERROR {
    }

    public static final String ERROR_NONE_NETWORK = "1";
    public static final String ERROR_TIMEOUT = "2";
    public static final String ERROR_RESULT_ILLEGAL = "3";

    private String code;

    private String msg;

    private static IExceptionDeclare exceptionDeclare;

    public DataLayerException(@DATA_LAYER_ERROR String code) {
        this.code = code;
    }

    public DataLayerException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        if (!TextUtils.isEmpty(msg)) {
            return msg;
        }

        if (!TextUtils.isEmpty(code) && exceptionDeclare != null) {
            String msg = exceptionDeclare.declareMessage(code);
            if (!TextUtils.isEmpty(msg)) {
                return msg;
            }
        }

        switch (code) {
            case ERROR_NONE_NETWORK:
                return "没有网络连接";
            case ERROR_TIMEOUT:
                return "网络不给力";
            case ERROR_RESULT_ILLEGAL:
                return "数据解析出错";
        }

        return super.getMessage();
    }

    public static void config(IExceptionDeclare declare) {
        DataLayerException.exceptionDeclare = declare;
    }
}
