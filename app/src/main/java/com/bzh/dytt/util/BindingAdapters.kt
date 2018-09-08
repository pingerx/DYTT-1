package com.bzh.dytt.util

import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.ActionBar
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bzh.dytt.R
import com.bzh.dytt.di.GlideApp
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette


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


@BindingAdapter("imageUrl", "actionBar")
fun paletteImage(view: ImageView, imageUrl: String?, actionBar: ActionBar) {
    if (imageUrl?.isNotEmpty() == true) {
        val glidePalette = GlidePalette.with(imageUrl)
                .use(BitmapPalette.Profile.MUTED_DARK)
                .intoBackground(view.parent as ViewGroup)
                .crossfade(true)
                .intoCallBack { palette ->
                    if (palette != null) {
                        actionBar.setBackgroundDrawable(ColorDrawable(palette.getDarkMutedColor(Color.WHITE)))
                    }
                }
        GlideApp.with(view.context)
                .load(imageUrl)
                .listener(glidePalette)
                .placeholder(R.drawable.default_video)
                .into(view)
    }
}
