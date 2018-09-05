package com.bzh.dytt.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.bzh.dytt.AppExecutors
import com.bzh.dytt.R
import com.bzh.dytt.base.BaseFragment
import com.bzh.dytt.databinding.SingleListPageBinding
import com.bzh.dytt.di.GlideApp
import com.bzh.dytt.ui.home.HomeListFragment
import com.bzh.dytt.util.autoCleared
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import kotlinx.android.synthetic.main.item_home_child.view.*
import kotlinx.android.synthetic.main.single_list_page.*
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    private var binding by autoCleared<SingleListPageBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: SearchViewModel

    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var adapter: SearchListAdapter

    lateinit var editText: EditText

    private val searchActionListener: TextView.OnEditorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!TextUtils.isEmpty(v.text)) {
                val searchTarget = v.text.toString().trim()
                viewModel.setQuery(searchTarget)
                swipe_refresh_layout.isEnabled = true
                swipe_refresh_layout.isRefreshing = true
                val imm: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
                return@OnEditorActionListener true
            }
        }
        return@OnEditorActionListener false
    }

    private var listObserver: Observer<Resource<List<MovieDetail>>> = Observer { result ->

        swipe_refresh_layout.isEnabled = false
        swipe_refresh_layout.isRefreshing = false

        when (result?.status) {
            Status.ERROR -> {
                binding.errorLayout.visibility = View.VISIBLE
                binding.emptyLayout.visibility = View.GONE
            }
            Status.LOADING -> {
                binding.errorLayout.visibility = View.GONE
                binding.emptyLayout.visibility = View.GONE
            }
            Status.SUCCESS -> {
                if (result.data == null || result.data.isEmpty()) {
                    binding.emptyLayout.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                } else {
                    binding.emptyLayout.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE
                    adapter.submitList(result.data.filter { it.id != 22066 })
                }
            }
        }
    }

    override fun doCreate(savedInstanceState: Bundle?) {
        val softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        activity?.window?.setSoftInputMode(softInputMode)
        super.doCreate(savedInstanceState)
        setupActionBar()
    }

    private fun setupActionBar() {
        if (activity is AppCompatActivity) {
            val actionBar = (activity as AppCompatActivity).supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false)
                actionBar.setDisplayShowCustomEnabled(true)
                val lp = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
                val customNav = LayoutInflater.from(activity).inflate(R.layout.search_action_bar, null)
                actionBar.setCustomView(customNav, lp)
                editText = customNav.findViewById(R.id.search_edit_input)
                editText.requestFocus()
            }
        }
    }

    override fun doCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<SingleListPageBinding>(inflater, R.layout.single_list_page, container, false)

        viewModel = viewModelFactory.create(SearchViewModel::class.java)
        lifecycle.addObserver(viewModel)

        return binding.root
    }

    override fun doViewCreated(view: View, savedInstanceState: Bundle?) {
        super.doViewCreated(view, savedInstanceState)

        editText.setOnEditorActionListener(searchActionListener)

        swipe_refresh_layout.isEnabled = false
        swipe_refresh_layout.isRefreshing = false

        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager

        adapter = SearchListAdapter(appExecutors)
        binding.recyclerView.adapter = adapter

        lifecycle.addObserver(viewModel)
        viewModel.listLiveData.observe(this, listObserver)
    }

    override fun onDestroyView() {
        viewModel.listLiveData.removeObserver(listObserver)
        lifecycle.removeObserver(viewModel)
        super.onDestroyView()
    }

    inner class SearchListAdapter constructor(appExecutors: AppExecutors) : ListAdapter<MovieDetail, MovieItemHolder>(
            AsyncDifferConfig
                    .Builder<MovieDetail>(object : DiffUtil.ItemCallback<MovieDetail>() {
                        override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                            return oldItem.id == newItem.id
                                    && oldItem.categoryId == newItem.categoryId
                        }

                        override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                            return oldItem.simpleName == newItem.simpleName
                                    && oldItem.homePicUrl == newItem.homePicUrl

                        }
                    })
                    .setBackgroundThreadExecutor(appExecutors.diskIO())
                    .build()
    ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_child, parent, false)
            return MovieItemHolder(view)
        }

        override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {

            val item = getItem(position)

            // clear value
            holder.itemView.video_title.text = ""
            holder.itemView.video_publish_time.text = ""
            holder.itemView.douban_grade.text = ""
            holder.itemView.imdb_grade.text = ""
            holder.itemView.video_description.text = ""
            holder.itemView.setOnClickListener(null)
            GlideApp.with(holder.itemView.context)
                    .load("")
                    .placeholder(R.drawable.default_video)
                    .into(holder.itemView.video_cover)

            // update value
            Log.d(TAG, "id=${item.id} categoryId=${item.categoryId} ${item.name} ${item.productArea} ${item.translateName} ${item.titleName} ")

            when {
                item.translateName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
                    holder.itemView.video_title.text = item.translateName
                }
                item.titleName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
                    holder.itemView.video_title.text = item.titleName
                }
                else -> {
                    holder.itemView.video_title.text = item.simpleName
                }
            }

            holder.itemView.video_publish_time.text = item.publishTime
            holder.itemView.video_description.text = item.description
            if (item.doubanGrade != 0F) {
                holder.itemView.douban_grade.text = "DB/${item.doubanGrade}"
            }
            if (item.imdbGrade != 0F) {
                holder.itemView.imdb_grade.text = "IMDB/${item.imdbGrade}"
            }
            if (!TextUtils.isEmpty(item.homePicUrl)) {
                GlideApp.with(holder.itemView.context)
                        .load(item.homePicUrl)
                        .placeholder(R.drawable.default_video)
                        .into(holder.itemView.video_cover)
            }

            holder.itemView.setOnClickListener {
                //                SingleActivity.startDetailPage(activity, item, "cover", movieDetail)
            }
        }
    }

    inner class MovieItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private const val TAG = "HomeListFragment"

        fun newInstance(): SearchFragment {
            val bundle = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}