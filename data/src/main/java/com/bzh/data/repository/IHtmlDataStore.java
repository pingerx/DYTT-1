package com.bzh.data.repository;

import com.bzh.data.exception.TaskException;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IHtmlDataStore {


    String TO_CHARSET_NAME = "GB2312";

    /**
     * 将html解析成字符串
     */
    Func1<ResponseBody, String> transformCharset = new Func1<ResponseBody, String>() {
        @Override
        public String call(ResponseBody responseBody) {
            try {
                return new String(responseBody.bytes(), TO_CHARSET_NAME);
            } catch (IOException e) {
                e.printStackTrace();
                throw new TaskException(TaskException.ERROR_HTML_PARSE);
            }
        }
    };
}
