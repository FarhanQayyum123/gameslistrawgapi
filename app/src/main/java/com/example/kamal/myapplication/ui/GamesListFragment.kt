package com.example.kamal.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kamal.myapplication.R
import com.example.kamal.myapplication.adapter.GamesListAdapter
import com.example.kamal.myapplication.model.GamesListModel
import com.example.kamal.myapplication.ui.Activity.ApiRequestParam.gameReqParamModel
import com.example.kamal.myapplication.utils.Constants
import com.example.kamal.myapplication.utils.Utils
import com.example.kamal.myapplication.viewModel.GamesViewModel
import java.util.*

class GamesListFragment: Fragment() {

    private var mViewModel: GamesViewModel? = null
    private val gamesList = ArrayList<GamesListModel>()
    private var page = 1
    private var isLoading = false
    private var isLastPage = false
    private var gamesListAdapter: GamesListAdapter? = null
    private var rvGamesList: RecyclerView? = null
    private var pbInitialLoader: ProgressBar? = null
    private  var pbLoadMore:ProgressBar? = null
    private var svNested: NestedScrollView? = null
    private var tvError: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.games_list_fragment, container, false)
        view?.let { initViews(it) }
        listeners()
        // Setting adapter here
        gamesListAdapter = GamesListAdapter(gamesList, activity, mViewModel!!)
        rvGamesList!!.layoutManager = LinearLayoutManager(activity)
        rvGamesList!!.adapter = gamesListAdapter
        // Set title here for listing screen
        requireActivity().title = requireActivity().resources.getString(R.string.master_screen)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        setObservers()
        apiCalling(page)
    }

    private fun initViews(view: View) {
        // init views here
        tvError = view.findViewById(R.id.tvError)
        svNested = view.findViewById(R.id.svNested)
        rvGamesList = view.findViewById(R.id.rvGamesList)
        pbInitialLoader = view.findViewById(R.id.pbInitialLoader)
        pbLoadMore = view.findViewById(R.id.pbLoadMore)
    }

    private fun listeners() {
        // adding on scroll change listener method for our nested scroll view.
        svNested!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                if (!isLoading && !isLastPage) { // For avoid multiple calls at the same time and checking that is more data still available
                    page++
                    apiCalling(page)
                }
            }
        })
    }

    private fun apiCalling(page: Int) {
        if (activity?.let { Utils.isNetworkConnected(it) } == true) {
            isLoading = !isLoading
            gameReqParamModel.page = page
            mViewModel!!.callAPI(Constants.GAMES_LIST, true, gameReqParamModel)
        } else errorMessageShow(requireActivity().resources.getString(R.string.no_internet))
    }

    private fun setObservers() {
        // Observer for listening games list data
        mViewModel!!.gamesList().observe(requireActivity(), { responseHttp -> // Here i am checking that there is no more pagination
            if (page > 1 && responseHttp!!.results != null && (responseHttp.results?.size == 0 || responseHttp.results?.size!! < 10)) isLastPage = true

            // This "if" condition means no data available for listing
            if (page == 1 && (responseHttp!!.results == null || responseHttp.results?.size == 0)) errorMessageShow(requireActivity().resources.getString(R.string.no_data_available)) else {
                responseHttp!!.results?.let { gamesList.addAll(it) }
                gamesListAdapter!!.notifyDataSetChanged()
                isLoading = !isLoading
                tvError!!.visibility = View.GONE
                rvGamesList!!.visibility = View.VISIBLE
                println("Its value of api call 1.1 " + gamesList.size)
            }
        })
        // Observer for listening loader hide/show
        mViewModel!!.isLoading.observe(requireActivity(), { isLoading ->
            if (isLoading != null) {
                if (isLoading) {
                    if (page == 1) pbInitialLoader!!.visibility = View.VISIBLE else if (!isLastPage) pbLoadMore!!.visibility = View.VISIBLE
                } else {
                    pbInitialLoader!!.visibility = View.GONE
                    pbLoadMore!!.visibility = View.GONE
                }
            }
        })

        // Observer for listening any type of error
        mViewModel!!.errorListener().observe(requireActivity(), { error ->
            var error = error
            if (error == null) error = requireActivity().resources.getString(R.string.something_went_wrong)
            errorMessageShow(error)
        })

        // Observer for listening any type of error
        mViewModel!!.getClickedItemObject().observe(requireActivity(), { itemObject -> (activity as Activity?)!!.loadFragment(ItemDetailFragment.newInstance(), itemObject!!.id.toString()) })
    }

    private fun errorMessageShow(error: kotlin.String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
        if (view != null && page == 1 && gamesList.size == 0) {
            tvError!!.visibility = View.VISIBLE
            tvError!!.text = error
            rvGamesList!!.visibility = View.GONE
        }
    }

}