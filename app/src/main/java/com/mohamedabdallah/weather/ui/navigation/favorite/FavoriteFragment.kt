package com.mohamedabdallah.weather.ui.navigation.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.ui.navigation.favorite.adapter.FavoriteListAdapter

class FavoriteFragment : Fragment(),FavoriteListAdapter.OnEditFavoriteListener {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private lateinit var viewModel: FavoriteViewModel
    private  var favoriteListAdapter=FavoriteListAdapter(emptyList(),this)
    private lateinit var favoriteListRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.favorite_fragment, container, false)
        favoriteListRecyclerView=view.findViewById(R.id.edit_recycler_view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        // TODO: Use the ViewModel

        favoriteListRecyclerView.adapter = favoriteListAdapter
        favoriteListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favoriteListRecyclerView.setHasFixedSize(true)

        viewModel.getFavoritePlaces().observe(viewLifecycleOwner, Observer {

            favoriteListAdapter.setData(it)
        })
    }
    override fun onRemovePlaceClick(favoritePlace: FavoritePlace) {
        viewModel.deleteFavoritePlace(favoritePlace)
    }

}