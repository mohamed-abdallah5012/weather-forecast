package com.mohamedabdallah.weather.ui.navigation.daily
import android.graphics.drawable.AnimationDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.ui.navigation.daily.adapter.DailyForecastAdapter
import com.mohamedabdallah.weather.ui.navigation.daily.adapter.DailyViewPagerAdapter
import com.mohamedabdallah.weather.ui.navigation.home.HomeViewModel
import com.mohamedabdallah.weather.utils.Constant

class DailyFragment : Fragment(),com.mohamedabdallah.weather.ui.navigation.daily.adapter.DailyForecastAdapter.OnDailyForecastListener{

    companion object {
        fun newInstance() =
            DailyFragment()
    }

    private lateinit var viewModel: DailyViewModel
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var constraintLayout: CoordinatorLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var frameLayout: FrameLayout
    private lateinit var viewPager: ViewPager2
    //
    private val dailyRecyclerViewAdapter =DailyForecastAdapter(emptyList(),this)
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
        viewModel = ViewModelProvider(this).get(DailyViewModel::class.java)

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

        viewPager.adapter=viewPagerAdapter
        viewPager.setCurrentItem(1, false)
        getWeatherForecast(Constant.currentLat,Constant.currentLon)
        bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED

    }

    private fun getWeatherForecast(lat:Double,lan:Double) {
        viewModel.getForecastOneApi(
            lat,lan,
            Constant.appID,
            Constant.api_lang,
            Constant.baseUnit
        )
            ?.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    viewPagerAdapter.setData(it.daily)
                }
                if (it != null) {
                    dailyRecyclerViewAdapter.setData(it.daily)
                }
                if (it != null) {
                    Log.i("TAG", "size:${it.daily.size}")
                }
            })
    }

    override fun onDailyForecastItemClick(id: Int) {
        viewPager.setCurrentItem(id, false)
        bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
    }


}