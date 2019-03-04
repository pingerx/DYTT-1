package com.bzh.dytt.view

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonInteractiveViewPage : androidx.viewpager.widget.ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent) = false

    override fun onTouchEvent(ev: MotionEvent) = false
}
