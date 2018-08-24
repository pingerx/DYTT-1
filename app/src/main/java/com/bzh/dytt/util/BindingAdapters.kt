package com.bzh.dytt.util

import android.databinding.BindingAdapter
import android.widget.TextView


/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {

    @BindingAdapter("format", "argId")
    fun setFormattedText(textView: TextView, format: String, argId: Int) {
        if (argId == 0) return
        textView.text = String.format(format, textView.resources.getString(argId))
    }
}
