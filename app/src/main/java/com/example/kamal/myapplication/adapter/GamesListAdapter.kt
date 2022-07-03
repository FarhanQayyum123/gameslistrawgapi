package com.example.kamal.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kamal.myapplication.R
import com.example.kamal.myapplication.model.GamesListModel
import com.example.kamal.myapplication.viewModel.GamesViewModel
import java.util.*

class GamesListAdapter(gamesList: ArrayList<GamesListModel>, context: Context?, mViewModel: GamesViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // variable for our array list and context.
    var gamesList: ArrayList<GamesListModel> = gamesList
    var context: Context? = context
    var mViewModel: GamesViewModel? = mViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // inflating our layout file on below line.
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.game_list_rv_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listViewHolder = holder as ListViewHolder

        // getting data from our array list in our modal class.
        val userModal = gamesList[position]

        // on below line we are setting data to our text view.
        listViewHolder.tvID.text = "ID: " + userModal.id
        listViewHolder.tvName.text = "Name: " + userModal.name
        listViewHolder.tvRating.text = "Rating: " + userModal.rating

        // on below line we are loading our image
        // from url in our image view using Glide.
        Glide
                .with(context!!)
                .load(userModal.background_image)
                .centerCrop()
                .override(200, 200)
                .into(listViewHolder.ivBanner)
    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return gamesList.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // creating a variable for our text view and image view.
        val tvID: TextView = itemView.findViewById(R.id.tvID)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val ivBanner: ImageView = itemView.findViewById(R.id.ivBanner)
        val rlParent: RelativeLayout = itemView.findViewById(R.id.rlParent)
        override fun onClick(v: View) {
            when (v.id) {
                R.id.rlParent -> mViewModel?.clickedItemObject?.setValue(gamesList[adapterPosition])
            }
        }

        init {

            // initializing our variables.
            rlParent.setOnClickListener { v: View -> onClick(v) }
        }
    }
}