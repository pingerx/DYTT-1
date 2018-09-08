package com.bzh.dytt.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
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
import com.bzh.dytt.databinding.SingleListPageBinding
import com.bzh.dytt.util.autoCleared
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class HomeListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var isLoadMore: Boolean = false

    private var refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        listViewModel.doRefreshFirstPage()
    }

    private var onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (recyclerView?.layoutManager is LinearLayoutManager) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val itemCount = linearLayoutManager.itemCount
                val completelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoadMore && completelyVisibleItemPosition == (itemCount - 1)) {
                    isLoadMore = true
                    onLoadMore()
                }
            }
        }
    }

    private var listObserver: Observer<Resource<List<MovieDetail>>> = Observer { result ->

        when (result?.status) {
            Status.ERROR -> {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.errorLayout.visibility = View.VISIBLE
                binding.emptyLayout.visibility = View.GONE
                isLoadMore = false

            }
            Status.LOADING -> {
                binding.swipeRefreshLayout.isRefreshing = true
                binding.errorLayout.visibility = View.GONE
                binding.emptyLayout.visibility = View.GONE
            }
            Status.SUCCESS -> {
                binding.swipeRefreshLayout.isRefreshing = false
                if (result.data == null || result.data.isEmpty()) {
                    binding.emptyLayout.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                } else {
                    binding.emptyLayout.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE
                    homeListAdapter.submitList(result.data.filter { it.id != 22066 })
                }
                isLoadMore = false
            }
        }
    }

    private val refreshObserver: Observer<Boolean> = Observer {
        binding.swipeRefreshLayout.isRefreshing = (it == true)
    }

    private lateinit var listViewModel: HomeListViewModel

    private lateinit var homeListAdapter: HomeListAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private var binding by autoCleared<SingleListPageBinding>()

    fun onLoadMore() {
        listViewModel.doLoadMorePage()
    }

    override fun doCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<SingleListPageBinding>(inflater, R.layout.single_list_page, container, false)

        listViewModel = viewModelFactory.create(HomeListViewModel::class.java)
        lifecycle.addObserver(listViewModel)

        return binding.root
    }

    override fun doViewCreated(view: View, savedInstanceState: Bundle?) {
        super.doViewCreated(view, savedInstanceState)

        val movieType = arguments?.getSerializable(MOVIE_TYPE)

        listViewModel.moveTypeLiveData.value = movieType as HomeViewModel.HomeMovieType
        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)

        binding.recyclerView.addOnScrollListener(onScrollListener)

        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager

        homeListAdapter = HomeListAdapter(appExecutors)
        binding.recyclerView.adapter = homeListAdapter

        listViewModel.movieListLiveData.observe(this, listObserver)

        listViewModel.refresLiveData.observe(this, refreshObserver)
    }

    override fun doDestroyView() {
        listViewModel.movieListLiveData.removeObserver(listObserver)
        listViewModel.refresLiveData.removeObserver(refreshObserver)
        lifecycle.removeObserver(listViewModel)
        super.doDestroyView()
    }

    inner class HomeListAdapter constructor(appExecutors: AppExecutors) : ListAdapter<MovieDetail, MovieItemHolder>(AsyncDifferConfig
            .Builder<MovieDetail>(object : DiffUtil.ItemCallback<MovieDetail>() {
                override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                    return oldItem.id == newItem.id && oldItem.categoryId == newItem.categoryId
                }

                override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
                    return oldItem.simpleName == newItem.simpleName && oldItem.homePicUrl == newItem.homePicUrl
                }
            })
            .setBackgroundThreadExecutor(appExecutors.diskIO())
            .build()
    ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieItemHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_home_child,
                        parent,
                        false
                )
        )

        override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {
            getItem(position).let { movieDetail ->
                Log.d(TAG, "onBindViewHolder() called with: movieDetail = [${movieDetail.id} ${movieDetail.name}]")
                with(holder) {
                    itemView.tag = movieDetail
                    bind(movieDetail)
                }
            }
        }

        override fun onViewAttachedToWindow(holder: MovieItemHolder) {
            super.onViewAttachedToWindow(holder)
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                homeListAdapter.getItem(holder.adapterPosition).let {
                    listViewModel.doUpdateMovieDetail(it)
                }
            }
        }

        override fun onViewDetachedFromWindow(holder: MovieItemHolder) {
            super.onViewDetachedFromWindow(holder)
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                homeListAdapter.getItem(holder.adapterPosition).let {
                    listViewModel.doRemoveUpdateMovieDetail(it)
                }
                holder.unbind()
            }
        }
    }

    inner class MovieItemHolder(val binding: ItemHomeChildBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieDetail: MovieDetail) {
            with(binding) {
                viewModel = ItemChildViewModel(movieDetail)
                viewModel?.clickObserver?.observe(viewLifecycleOwner, Observer {
                    SingleActivity.startDetailPage(activity, binding.videoCover, binding.videoCover.transitionName, movieDetail)
                })
                executePendingBindings()
            }
        }

        fun unbind() {
            binding.videoCover.setImageResource(R.drawable.default_video)
        }
    }

    companion object {

        const val PATTERN = "[\\u4e00-\\u9fa5]"

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