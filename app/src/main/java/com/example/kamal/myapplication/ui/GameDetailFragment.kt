package com.example.kamal.myapplication.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.kamal.myapplication.R
import com.example.kamal.myapplication.ui.Activity.ApiRequestParam.gameReqParamModel
import com.example.kamal.myapplication.utils.Constants
import com.example.kamal.myapplication.utils.Utils
import com.example.kamal.myapplication.viewModel.GamesViewModel

class GameDetailFragment : Fragment() {
    private var itemId: String? = ""
    private var mViewModel: GamesViewModel? = null
    private var progressBarDetail: ProgressBar? = null
    private var tvItemId: TextView? = null
    private var tvItemName: TextView? = null
    private var tvItemUpdated: TextView? = null
    private var tvItemDescription: TextView? = null
    private var ivBanner: ImageView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_detail_page, container, false)
        initViews(view)
        setObservers()
        // set title here for detail page
        requireActivity().title = requireActivity().resources.getString(R.string.detail_page)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) itemId = requireArguments().getString("gameItemId")
        mViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        apiCalling(itemId)
    }

    private fun initViews(view: View) {
        // init views here
        tvItemId = view.findViewById(R.id.tvItemId)
        tvItemName = view.findViewById(R.id.tvItemName)
        tvItemUpdated = view.findViewById(R.id.tvItemUpdated)
        tvItemDescription = view.findViewById(R.id.tvItemDescription)
        ivBanner = view.findViewById(R.id.ivBanner)
        progressBarDetail = view.findViewById(R.id.progressBarDetail)
    }

    private fun apiCalling(itemId: String?) {
        if (activity?.let { Utils.NetWork.isNetworkConnected(it) } == true) {
            gameReqParamModel.itemId = itemId
            mViewModel!!.callAPI(Constants.Tags.ITEM_DETAIL, true, gameReqParamModel)
        } else errorMessageShow(requireActivity().resources.getString(R.string.no_internet))
    }

    private fun setObservers() {

        // Observer for listening games list data
        mViewModel!!.itemDetail.observe(requireActivity(), { response ->
            Glide
                    .with(requireContext())
                    .load(response!!.background_image)
                    .override(500, 500)
                    .into(ivBanner!!)
            tvItemId!!.text = "ID: " + response.id
            tvItemName!!.text = "Name: " + response.name
            tvItemUpdated!!.text = "Update At: " + response.updated
            tvItemDescription!!.text = "Description: \n${Html.fromHtml(response.description)}"
        })

        // Observer for listening loader hide/show
        mViewModel!!.isLoading.observe(requireActivity(), { isLoading ->
            if (isLoading != null) {
                if (isLoading) progressBarDetail!!.visibility = View.VISIBLE else progressBarDetail!!.visibility = View.GONE
            }
        })

        // Observer for listening any type of error
        mViewModel!!.errorListener().observe(requireActivity(), { error ->
            var error = error
            if (error == null) error = requireActivity().resources.getString(R.string.something_went_wrong)
            errorMessageShow(error)
        })
    }

    private fun errorMessageShow(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(): GameDetailFragment {
            return GameDetailFragment()
        }
    }
}