package com.bzh.dytt.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.analytics.FirebaseAnalytics


abstract class BaseActivity : AppCompatActivity() {

//    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain the FirebaseAnalytics instance.
//        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}