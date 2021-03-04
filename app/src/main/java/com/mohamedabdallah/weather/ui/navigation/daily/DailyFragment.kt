package com.mohamedabdallah.weather.ui.navigation.daily
import android.graphics.drawable.AnimationDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.mohamedabdallah.weather.R

class DailyFragment : Fragment() {

    companion object {
        fun newInstance() =
            DailyFragment()
    }

    private lateinit var viewModel: DailyViewModel
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var constraintLayout: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.daily_fragment, container, false)
        constraintLayout=view.findViewById(R.id.daily_fragment_test)
        animationDrawable= constraintLayout.background as AnimationDrawable

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DailyViewModel::class.java)

        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(2000)
            start()
        }
    }

}