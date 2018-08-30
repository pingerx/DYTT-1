package com.bzh.dytt

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.bzh.dytt.base.BaseActivity
import com.bzh.dytt.base.BaseFragment
import com.bzh.dytt.databinding.ActivityMainBinding
import com.bzh.dytt.ui.home.HomeFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var pagerAdapter: MainViewPagerAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.toolbar.setTitle(R.string.nav_home_page)
        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        pagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        binding.contentContainer.adapter = pagerAdapter

//        MobileAds.initialize(this, ADMOB_APP_ID)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_search) {
            SingleActivity.startSearchPage(this)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.nav_home -> {
                binding.toolbar.setTitle(R.string.nav_home_page)
                binding.contentContainer.currentItem = 0
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    override fun supportFragmentInjector() = fragmentInjector

    class MainViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): BaseFragment = when (position) {
            0 -> HomeFragment.newInstance()
            else -> throw IndexOutOfBoundsException()
        }

        override fun getCount() = 1
    }

    companion object {

        // ca-app-pub-2810447214027158/1355816417
        const val ADMOB_APP_ID_BZH = "ca-app-pub-2810447214027158~8679772669"

        const val ADMOB_APP_ID = "ca-app-pub-8112052667906046~4830848371"
    }
}