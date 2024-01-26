package com.example.quizn8.presentation.screen.home

import android.util.Log
import android.util.Log.d
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizn8.databinding.DrawerMenuBinding
import com.example.quizn8.databinding.FragmentHomeBinding
import com.example.quizn8.presentation.base.BaseFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    GestureDetector.OnGestureListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var drawerItemsBinding: DrawerMenuBinding
    private val drawerRecyclerViewAdapter = DrawableItemsRecyclerViewAdapter()
    private val viewModel: HomeVIewModel by viewModels()


    override fun setUp() {
        gestureDetector = GestureDetectorCompat(requireContext(), this)
        with(binding) {
            drawer = drawerLayout
            navigationView = navView

            drawerItemsBinding = DrawerMenuBinding.inflate(layoutInflater, navigationView, false)
            navigationView.addView(drawerItemsBinding.root)
        }

        drawerItemsBinding.recyclerDrawbaleItems.layoutManager = LinearLayoutManager(requireContext())
        drawerItemsBinding.recyclerDrawbaleItems.adapter = drawerRecyclerViewAdapter.apply {
            onClick = {
                viewModel.selectItem(it)
            }
        }
    }

    override fun setUpListeners() {
        drawerListener()
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.drawerItemsStateFlow.collect {
                    drawerRecyclerViewAdapter.submitList(it)
                }
            }
        }
    }



    private fun drawerListener() {
        drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean = false

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

        e1 ?: return false
        Log.d("GestureDebug", "Fling detected")
        drawer.openDrawer(GravityCompat.START)

        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {}

    override fun onSingleTapUp(e: MotionEvent): Boolean = false
}
