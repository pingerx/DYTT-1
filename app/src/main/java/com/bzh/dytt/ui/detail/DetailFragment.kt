package com.bzh.dytt.ui.detail

import android.app.Dialog
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
import com.bzh.dytt.util.ThunderHelper
import com.bzh.dytt.vo.MovieDetail
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.video_detail_layout.*

class DetailFragment : BaseFragment(), Injectable {

    var thunderHelper: ThunderHelper = ThunderHelper()

    var movieDetail: MovieDetail? = null

    override fun doCreate(savedInstanceState: Bundle?) {
        super.doCreate(savedInstanceState)
        if (arguments != null) {
            movieDetail = arguments?.getParcelable<MovieDetail>(MOVIE_DETAIL)
        }
    }

    override fun doCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.video_detail_layout, container, false)
    }

    override fun doViewCreated(view: View, savedInstanceState: Bundle?) {
        super.doViewCreated(view, savedInstanceState)

        if (movieDetail == null) {
            return
        }

        if (activity != null) {
            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.title = movieDetail?.simpleName
        }

        video_type.text = movieDetail?.type
        video_country.text = movieDetail?.productArea
        video_duration.text = movieDetail?.duration
        video_show_time.text = movieDetail?.publishTime

        if (movieDetail?.doubanGrade != null) {
            douban_grade.text = getString(R.string.douban_grade, movieDetail?.doubanGrade)
            douban_rating_bar.rating = movieDetail?.doubanGrade!! / 2
            douban_rating_layout.visibility = View.VISIBLE
        } else {
            douban_rating_layout.visibility = View.GONE
        }

        if (movieDetail?.imdbGrade != null) {
            imdb_grade.text = getString(R.string.imdb_grade, movieDetail?.imdbGrade)
            imdb_rating_bar.rating = movieDetail?.imdbGrade!! / 2
            imdb_rating_layout.visibility = View.VISIBLE
        } else {
            imdb_rating_layout.visibility = View.GONE
        }

        video_director.text = movieDetail?.diretor

        video_description.text = movieDetail?.description
        video_description.setOnClickListener({ view ->
            if ((view as TextView).maxLines > 4) {
                view.maxLines = 4
            } else {
                view.maxLines = 100
            }
        })
        download_button.setOnClickListener({
            if (activity == null || !thunderHelper.onClickDownload(activity, movieDetail?.downloadUrl)) {
                val dialogFragment = InnerDialogFragment()
                dialogFragment.show(activity!!.supportFragmentManager, "InnerDialog")
            }
        })
        if (movieDetail?.homePicUrl?.isNotEmpty()!!) {

            val glidePalette = GlidePalette.with(movieDetail?.homePicUrl)
//                    .use(GlidePalette.Profile.MUTED_DARK)
                    .intoBackground(video_cover_bg)
                    .crossfade(true)
                    .intoCallBack { palette ->
                        if (palette != null && activity != null) {
                            val actionBar = (activity as AppCompatActivity).supportActionBar
                            actionBar?.setBackgroundDrawable(ColorDrawable(palette.getDarkMutedColor(Color.WHITE)))
                        }
                    }

            GlideApp.with(this)
                    .load(movieDetail?.homePicUrl)
                    .listener(glidePalette)
                    .placeholder(R.drawable.default_video)
                    .into(video_cover)
        }
    }

    companion object {

        private const val TAG = "DetailFragment"

        public const val MOVIE_DETAIL = "MOVIE_DETAIL"

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