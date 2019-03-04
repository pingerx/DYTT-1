package com.bzh.dytt.ui.search

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.bzh.dytt.databinding.SearchListPageBinding
import com.bzh.dytt.ui.home.HomeListAdapter
import com.bzh.dytt.ui.home.MovieItemHolder
import com.bzh.dytt.util.autoCleared
import com.bzh.dytt.vo.MovieDetail
import kotlinx.android.synthetic.main.home_list_page.*
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    private var binding by autoCleared<SearchListPageBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: SearchViewModel

    lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager

    lateinit var adapter: SearchListAdapter

    lateinit var editText: EditText

    private val refreshObserver: Observer<Boolean> = Observer {
        if (it != null) {
            swipe_refresh_layout.isEnabled = it
            swipe_refresh_layout.isRefreshing = it
        } else {
            swipe_refresh_layout.isEnabled = false
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private val searchActionListener: TextView.OnEditorActionListener = TextView.OnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!TextUtils.isEmpty(v.text)) {
                val searchTarget = v.text.toString().trim()
                viewModel.setQuery(searchTarget)

                val imm: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
                return@OnEditorActionListener true
            }
        }
        return@OnEditorActionListener false
    }

    private var listObserver: Observer<List<MovieDetail>> = Observer { result ->
        adapter.submitList(result)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.search_list_page, container, false)

        viewModel = viewModelFactory.create(SearchViewModel::class.java)
        lifecycle.addObserver(viewModel)
        binding.viewModel = viewModel
        viewModel.movieListLiveData.observe(this, listObserver)
        viewModel.refreshLiveData.observe(this, refreshObserver)

        return binding.root
    }

    override fun doViewCreated(view: View, savedInstanceState: Bundle?) {
        super.doViewCreated(view, savedInstanceState)

        editText.setOnEditorActionListener(searchActionListener)

        linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager

        adapter = SearchListAdapter(activity, viewModel, appExecutors)
        binding.recyclerView.adapter = adapter
    }

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

class SearchListAdapter constructor(val activity: androidx.fragment.app.FragmentActivity?, val viewModel: SearchViewModel, appExecutors: AppExecutors) : ListAdapter<MovieDetail, MovieItemHolder>(
        AsyncDifferConfig
                .Builder<MovieDetail>(object : DiffUtil.ItemCallback<MovieDetail>() {
                    override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                        return oldItem.id == newItem.id
                                && oldItem.categoryId == newItem.categoryId
                    }

                    override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                        return oldItem.name == newItem.name
                                && oldItem.homePicUrl == newItem.homePicUrl
                    }
                })
                .setBackgroundThreadExecutor(appExecutors.diskIO())
                .build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
        return MovieItemHolder(activity,
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_home_child,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {
        getItem(position).let { movieDetail ->
            Log.d(HomeListAdapter.TAG, "onBindViewHolder() called with: movieDetail = [${movieDetail.id} ${movieDetail.name}]")
            with(holder) {
                itemView.tag = movieDetail
                reset()
                bind(movieDetail)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: MovieItemHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
            getItem(holder.adapterPosition).let {
                viewModel.doUpdateMovieDetail(it)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: MovieItemHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
            getItem(holder.adapterPosition).let {
                viewModel.doRemoveUpdateMovieDetail(it)
            }
        }
    }
}
