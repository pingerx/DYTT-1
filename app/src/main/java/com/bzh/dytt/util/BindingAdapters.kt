package com.bzh.dytt.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bzh.dytt.R
import com.bzh.dytt.di.GlideApp


/**
 * Data Binding adapters specific to the app.
 */
@BindingAdapter("format", "argId")
fun setFormattedText(textView: TextView, format: String, argId: Int) {
    if (argId == 0) return
    textView.text = String.format(format, textView.resources.getString(argId))
}


@BindingAdapter("imageFromUrl")
fun imageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view.context)
                .load(imageUrl)
                .placeholder(R.drawable.default_video)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

