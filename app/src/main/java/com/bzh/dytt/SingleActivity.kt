package com.bzh.dytt

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.view.MenuItem
import android.widget.ImageView
import com.bzh.dytt.base.BaseActivity
import com.bzh.dytt.base.BaseFragment
import com.bzh.dytt.ui.detail.DetailFragment
import com.bzh.dytt.ui.search.SearchFragment
import com.bzh.dytt.vo.MovieDetail
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class SingleActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    override fun supportFragmentInjector() = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        setupActionBar()
        setupContainerWithType()
    }

    private fun setupContainerWithType() {
        val type = intent.getSerializableExtra(TYPE)
        val fragment: BaseFragment = when (type) {
            SingleType.Detail -> {
                DetailFragment.newInstance(intent.getParcelableExtra(DATA))
            }
            else -> {
                SearchFragment.newInstance()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss()
    }

    private fun setupActionBar() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    enum class SingleType {
        Detail(),
        Search()
    }

    companion object {
        const val TYPE = "TYPE"
        const val DATA = "DATA"

        fun startDetailPage(activity: androidx.fragment.app.FragmentActivity?, sharedElement: ImageView, sharedKey: String, movieDetail: MovieDetail) {
            val intent = Intent(activity, SingleActivity::class.java)
            intent.putExtra(TYPE, SingleActivity.SingleType.Detail)
            intent.putExtra(DATA, movieDetail)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity?.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedKey).toBundle())
            } else {
                activity?.startActivity(intent)
            }
        }

        fun startSearchPage(activity: Activity) {
            val intent = Intent(activity, SingleActivity::class.java)
            intent.putExtra(TYPE, SingleActivity.SingleType.Search)
            activity.startActivity(intent)
        }
    }
}