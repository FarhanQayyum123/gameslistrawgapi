package com.example.kamal.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kamal.myapplication.R
import com.example.kamal.myapplication.model.APIRequestParamModel


class Activity : AppCompatActivity() {

    companion object ApiRequestParam {
        var gameReqParamModel = APIRequestParamModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        loadFragment(GamesListFragment(), "")
    }

    fun loadFragment(fragment: Fragment, itemId: String) {
        // Set game item clicked id here for pass to detail page
        val args = Bundle()
        args.putString("gameItemId", itemId)
        fragment.arguments = args
        // Call fragment here
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.container, fragment).addToBackStack("")
        transaction.commit()
    }

    // back press method to handle for load previous fragment instead of clos activity
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStackImmediate()
        else
            super.onBackPressed()
    }

}