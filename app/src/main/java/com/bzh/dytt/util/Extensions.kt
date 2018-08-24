package com.bzh.dytt.util

import com.google.gson.Gson
import org.json.JSONObject

val Gson = Gson()

fun Any.toJson(): String {
    val json = Gson.toJson(this)
    return JSONObject(json).toString(4)
}