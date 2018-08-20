package com.bzh.dytt.ui.detail

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
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
import com.bzh.dytt.di.GlideApp
import com.bzh.dytt.di.Injectable
import com.bzh.dytt.ui.home.HomeListFragment
import com.bzh.dytt.util.ThunderHelper
import com.bzh.dytt.vo.MovieDetail
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.video_detail_layout.*
import javax.inject.Inject


class DetailFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: DetailViewModel

    private var thunderHelper: ThunderHelper = ThunderHelper()

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
        if (activity != null) {
            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.title = movieDetail?.simpleName
            when {
                movieDetail?.translateName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
                    actionBar?.title = movieDetail.translateName
                }
                movieDetail?.titleName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
                    actionBar?.title = movieDetail.titleName
                }
                else -> {
                    actionBar?.title = movieDetail?.simpleName
                }
            }
        }

        video_type.text = movieDetail?.type
        video_country.text = movieDetail?.productArea
        video_duration.text = movieDetail?.duration
        video_show_time.text = movieDetail?.publishTime

        if (movieDetail?.doubanGrade != null) {
            douban_grade.text = getString(R.string.douban_grade, movieDetail.doubanGrade)
            douban_rating_bar.rating = movieDetail.doubanGrade / 2
            douban_rating_layout.visibility = View.VISIBLE
        } else {
            douban_rating_layout.visibility = View.GONE
        }

        if (movieDetail?.imdbGrade != null) {
            imdb_grade.text = getString(R.string.imdb_grade, movieDetail.imdbGrade)
            imdb_rating_bar.rating = movieDetail.imdbGrade / 2
            imdb_rating_layout.visibility = View.VISIBLE
        } else {
            imdb_rating_layout.visibility = View.GONE
        }

        video_director.text = movieDetail?.diretor

        video_description.text = movieDetail?.description

        video_description.setOnClickListener { view ->
            if ((view as TextView).maxLines > 4) {
                view.maxLines = 4
            } else {
                view.maxLines = 100
            }
        }

        download_button.setOnClickListener {
            if (activity == null || !thunderHelper.onClickDownload(activity, movieDetail?.downloadUrl)) {
                val dialogFragment = InnerDialogFragment()
                dialogFragment.show(activity!!.supportFragmentManager, "InnerDialog")
            }
        }

        if (movieDetail?.homePicUrl?.isNotEmpty() == true) {

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

    override fun doCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.video_detail_layout, container, false)
    }

    override fun doViewCreated(view: View, savedInstanceState: Bundle?) {
        super.doViewCreated(view, savedInstanceState)
        viewModel = viewModelFactory.create(DetailViewModel::class.java)
        lifecycle.addObserver(viewModel)
        if (arguments != null) {
            viewModel.paramsLiveData.value = arguments?.getParcelable(MOVIE_DETAIL)
        }
        viewModel.movieDetailLiveData.observe(this, movieDetailObserver)
        viewModel.refreshLiveData.observe(this, refreshObserver)

//        val adRequest = AdRequest.Builder().build()
//        adView.loadAd(adRequest)
    }

    override fun doDestroyView() {
        lifecycle.removeObserver(viewModel)
        viewModel.movieDetailLiveData.removeObserver(movieDetailObserver)
        viewModel.refreshLiveData.removeObserver(refreshObserver)
        super.doDestroyView()
    }

    companion object {

        private const val TAG = "DetailFragment"

        const val MOVIE_DETAIL = "MOVIE_DETAIL"

        fun newInstnace(movieDetail: MovieDetail): DetailFragment {
            val args = Bundle()
            args.putParcelable(MOVIE_DETAIL, movieDetail)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    class InnerDialogFragment : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage(R.string.un_install_xunlei_label).setPositiveButton(R.string.ok, null)
            return builder.create()
        }
    }
}