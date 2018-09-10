package com.bzh.dytt.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bzh.dytt.AppExecutors
import com.bzh.dytt.R
import com.bzh.dytt.SingleActivity
import com.bzh.dytt.base.BaseFragment
import com.bzh.dytt.databinding.ItemHomeChildBinding
import com.bzh.dytt.databinding.ItemLoadMoreBinding
import com.bzh.dytt.databinding.SingleListPageBinding
import com.bzh.dytt.util.autoCleared
import com.bzh.dytt.vo.MovieDetail
import javax.inject.Inject

class HomeListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.doRefreshFirstPage()
    }

    private var onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.layoutManager is LinearLayoutManager) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val itemCount = linearLayoutManager.itemCount
                val completelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!viewModel.isLoadMore && completelyVisibleItemPosition == (itemCount - 1)) {
                    viewModel.doLoadMorePage()
                }
            }
        }
    }

    private var listObserver: Observer<List<MovieDetail>> = Observer { result ->
        homeListAdapter.submitList(result)
    }

    private val refreshObserver: Observer<Boolean> = Observer {
        binding.swipeRefreshLayout.isRefreshing = (it == true)
    }

    private lateinit var viewModel: HomeListViewModel

    private lateinit var homeListAdapter: HomeListAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private var binding by autoCleared<SingleListPageBinding>()

    override fun doCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.single_list_page, container, false)

        viewModel = viewModelFactory.create(HomeListViewModel::class.java)
        lifecycle.addObserver(viewModel)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun doViewCreated(view: View, savedInstanceState: Bundle?) {
        super.doViewCreated(view, savedInstanceState)

        val movieType = arguments?.getSerializable(MOVIE_TYPE)

        viewModel.moveTypeLiveData.value = movieType as HomeViewModel.HomeMovieType
        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)

        binding.recyclerView.addOnScrollListener(onScrollListener)

        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager

        homeListAdapter = HomeListAdapter(activity, viewModel, appExecutors)
        binding.recyclerView.adapter = homeListAdapter

        viewModel.movieListLiveData.observe(this, listObserver)

        viewModel.refreshLiveData.observe(this, refreshObserver)
    }

    override fun doDestroyView() {
        viewModel.movieListLiveData.removeObserver(listObserver)
        viewModel.refreshLiveData.removeObserver(refreshObserver)
        lifecycle.removeObserver(viewModel)
        super.doDestroyView()
    }

    companion object {

        private const val TAG = "HomeListFragment"

        private const val MOVIE_TYPE = "MOVIE_TYPE"

        fun newInstance(movieType: HomeViewModel.HomeMovieType): HomeListFragment {
            val args = Bundle()
            args.putSerializable(MOVIE_TYPE, movieType)
            val fragment = HomeListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


class HomeListAdapter(val activity: FragmentActivity?, val viewModel: HomeListViewModel, appExecutors: AppExecutors) : ListAdapter<MovieDetail, RecyclerView.ViewHolder>(AsyncDifferConfig
        .Builder<MovieDetail>(object : DiffUtil.ItemCallback<MovieDetail>() {
            override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                return oldItem.id == newItem.id && oldItem.categoryId == newItem.categoryId
            }

            override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                return oldItem.name == newItem.name && oldItem.homePicUrl == newItem.homePicUrl
            }
        })
        .setBackgroundThreadExecutor(appExecutors.diskIO())
        .build()
) {


    override fun getItem(position: Int): MovieDetail {
        return if (position >= super.getItemCount()) {
            MovieDetail.createEmptyMovieDetail()
        } else {
            super.getItem(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= super.getItemCount()) {
            ITEM_LOAD_MORE_TYPE
        } else {
            super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() == 0) {
            super.getItemCount()
        } else {
            super.getItemCount() + ITEM_LOAD_MORE_COUNT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_LOAD_MORE_TYPE) {
            return LoadMoreItemHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                            R.layout.item_load_more,
                            parent, false
                    )
            )
        } else {
            return MovieItemHolder(activity,
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_home_child,
                            parent,
                            false
                    )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_LOAD_MORE_TYPE) {

        } else {
            getItem(position).let { movieDetail ->
                Log.d(TAG, "onBindViewHolder() called with: movieDetail = [${movieDetail.id} ${movieDetail.name}]")
                with(holder as MovieItemHolder) {
                    itemView.tag = movieDetail
                    reset()
                    bind(movieDetail)
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            getItem(holder.adapterPosition).let {
                viewModel.doUpdateMovieDetail(it)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            getItem(holder.adapterPosition).let {
                viewModel.doRemoveUpdateMovieDetail(it)
            }
        }
    }

    companion object {
        const val TAG = "HomeListAdapter"
        const val ITEM_LOAD_MORE_COUNT = 1
        const val ITEM_LOAD_MORE_TYPE = 1000
    }

}

class LoadMoreItemHolder(val binding: ItemLoadMoreBinding) : RecyclerView.ViewHolder(binding.root)


class MovieItemHolder(val activity: FragmentActivity?, val binding: ItemHomeChildBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieDetail: MovieDetail) {
        with(binding) {
            viewModel = ItemChildViewModel(movieDetail)
            if (activity != null) {
                viewModel?.clickObserver?.observe(activity, Observer {
                    SingleActivity.startDetailPage(activity, binding.videoCover, binding.videoCover.transitionName, movieDetail)
                })
            }
            executePendingBindings()
        }
    }

    fun reset() {
        binding.videoCover.setImageResource(R.drawable.default_video)
    }
}