package com.bzh.dytt.ui.detail

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bzh.dytt.R
import com.bzh.dytt.base.BaseFragment
import com.bzh.dytt.databinding.DetailFragmentBinding
import com.bzh.dytt.di.GlideApp
import com.bzh.dytt.di.Injectable
import com.bzh.dytt.ui.home.HomeListFragment
import com.bzh.dytt.util.ThunderHelper
import com.bzh.dytt.util.autoCleared
import com.bzh.dytt.vo.MovieDetail
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.detail_fragment.*
import javax.inject.Inject

class DetailFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailViewModel

    private var thunderHelper: ThunderHelper = ThunderHelper()

    private var binding by autoCleared<DetailFragmentBinding>()

    private var refreshObserver: Observer<Boolean> = Observer {
        if (it == true) {
            swipe_refresh_layout.isEnabled = true
            swipe_refresh_layout.isRefreshing = true
        } else {
            swipe_refresh_layout.isEnabled = false
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private var movieDetailObserver: Observer<MovieDetail> = Observer { movieDetail ->

        binding.movieDetail = movieDetail

        updateTitleBar(movieDetail!!)

        updateBackground(movieDetail!!)
    }

    private fun updateBackground(movieDetail: MovieDetail) {
        if (movieDetail.homePicUrl?.isNotEmpty() == true) {

            val glidePalette = GlidePalette.with(movieDetail.homePicUrl)
                    .use(BitmapPalette.Profile.MUTED_DARK)
                    .intoBackground(video_cover_bg)
                    .crossfade(true)
                    .intoCallBack { palette ->
                        if (palette != null && activity != null) {
                            val actionBar = (activity as AppCompatActivity).supportActionBar
                            actionBar?.setBackgroundDrawable(ColorDrawable(palette.getDarkMutedColor(Color.WHITE)))
                        }
                    }

            GlideApp.with(this)
                    .load(movieDetail.homePicUrl)
                    .listener(glidePalette)
                    .placeholder(R.drawable.default_video)
                    .into(video_cover)
        }
    }

    private fun updateTitleBar(movieDetail: MovieDetail) {
        if (activity != null) {
            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.title = movieDetail.simpleName
            when {
                movieDetail.translateName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
                    actionBar?.title = movieDetail.translateName
                }
                movieDetail.titleName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
                    actionBar?.title = movieDetail.titleName
                }
                else -> {
                    actionBar?.title = movieDetail.simpleName
                }
            }
        }
    }

    fun onClickDownload(movieDetail: MovieDetail) {
        if (activity == null || !thunderHelper.onClickDownload(activity, movieDetail.downloadUrl)) {
            val dialogFragment = InnerDialogFragment()
            dialogFragment.show(activity?.supportFragmentManager, "InnerDialog")
        }
    }

    fun onClickDescription(view: View) {
        if ((view as TextView).maxLines > 4) {
            view.maxLines = 4
        } else {
            view.maxLines = 100
        }
    }

    override fun doCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        return binding.root
    }

    override fun doViewCreated(view: View, savedInstanceState: Bundle?) {
        super.doViewCreated(view, savedInstanceState)

        binding.setLifecycleOwner(this)
        binding.detailFragment = this

        viewModel = viewModelFactory.create(DetailViewModel::class.java)
        lifecycle.addObserver(viewModel)
        binding.viewModel = viewModel

        if (arguments != null) {
            viewModel.paramsLiveData.value = arguments?.getParcelable(MOVIE_DETAIL)
        }

        viewModel.movieDetailLiveData.observe(this, movieDetailObserver)
        viewModel.swipeRefreshStatus.observe(this, refreshObserver)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    companion object {

        private const val TAG = "DetailFragment"

        const val MOVIE_DETAIL = "MOVIE_DETAIL"

        fun newInstance(movieDetail: MovieDetail): DetailFragment {
            val args = Bundle()
            args.putParcelable(MOVIE_DETAIL, movieDetail)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

class InnerDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage(R.string.un_install_xunlei_label).setPositiveButton(R.string.ok, null)
        return builder.create()
    }
}