package com.mohamedabdallah.weather.ui.navigation.daily
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.forecast.ForecastCustomizedModel
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.ui.navigation.daily.adapter.DailyViewPagerAdapter
import com.mohamedabdallah.weather.ui.navigation.home.HomeViewModel
import com.mohamedabdallah.weather.ui.navigation.home.adapter.DailyForecastAdapter
import com.mohamedabdallah.weather.ui.navigation.home.adapter.FavoriteCitiesAdapter
import com.mohamedabdallah.weather.utils.Constant

class DailyFragment : Fragment(),FavoriteCitiesAdapter.OnFavoritePlaceListener{

    companion object {
        fun newInstance() =
            DailyFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var constraintLayout: CoordinatorLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var frameLayout: FrameLayout
    private lateinit var viewPager: ViewPager2
    //
    private val dailyRecyclerViewAdapter = FavoriteCitiesAdapter(emptyList(),this)
    private val viewPagerAdapter = DailyViewPagerAdapter(emptyList())
    private lateinit var dailyRecyclerView :RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.daily_fragment, container, false)
        constraintLayout=view.findViewById(R.id.daily_fragment_test)
        animationDrawable= constraintLayout.background as AnimationDrawable
        frameLayout=view.findViewById(R.id.daily_bottom_sheet)
        viewPager=view.findViewById(R.id.daily_view_Pager )
        dailyRecyclerView=view.findViewById(R.id.daily_recycler_view )
        bottomSheetBehavior=BottomSheetBehavior.from(frameLayout)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        bottomSheetBehavior.apply {
            peekHeight=75
            this.state=BottomSheetBehavior.STATE_COLLAPSED
        }

        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(2000)
            start()
        }

        dailyRecyclerView.adapter = dailyRecyclerViewAdapter
        dailyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dailyRecyclerView.setHasFixedSize(true)

        val lis= arrayListOf<FavoritePlace>(
            FavoritePlace("5","name1","address",31.00,33.00,bitmap = Bitmap.createBitmap(5,6,Bitmap.Config.ALPHA_8)),
            FavoritePlace("5","name2","address2",31.00,33.00,bitmap = Bitmap.createBitmap(5,6,Bitmap.Config.ALPHA_8)),
            FavoritePlace("5","name3","address3",31.00,33.00,bitmap = Bitmap.createBitmap(5,6,Bitmap.Config.ALPHA_8)),
            FavoritePlace("5","name4","address4",31.00,33.00,bitmap = Bitmap.createBitmap(5,6,Bitmap.Config.ALPHA_8)),
            FavoritePlace("5","name5","address5",31.00,33.00,bitmap = Bitmap.createBitmap(5,6,Bitmap.Config.ALPHA_8)))
        viewPager.adapter=DailyViewPagerAdapter(lis)
        viewPager.setCurrentItem(2, false)
        getWeatherForecast("cairo")
    }

    private fun getWeatherForecast(cityName: String) {
        Log.i("TAG", "getWeatherForecast: method1")
        viewModel.getFavoritePlaces(

        )
            ?.observe(viewLifecycleOwner, Observer {

                dailyRecyclerViewAdapter.setData(it)
                viewPagerAdapter.setData(it)
            })

    }

    override fun onFavoritePlaceClick(favoritePlace: FavoritePlace) {
        bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
    }


}