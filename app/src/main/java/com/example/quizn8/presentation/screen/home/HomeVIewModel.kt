package com.example.quizn8.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizn8.R
import com.example.quizn8.presentation.model.DrawerItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeVIewModel: ViewModel() {
    private val _drawerItemsStateFlow = MutableStateFlow<List<DrawerItem>>(emptyList())
    val drawerItemsStateFlow = _drawerItemsStateFlow.asStateFlow()

    init {
        setData()
    }

    fun selectItem(settingItem: DrawerItem) {
        viewModelScope.launch {
            val updatedList = _drawerItemsStateFlow.value.map { currentItem ->
                if (currentItem == settingItem) {
                    currentItem.copy(isSelected = !currentItem.isSelected)
                } else {
                    currentItem
                }
            }
            _drawerItemsStateFlow.value = updatedList
        }
    }

    private fun setData() {
        val data = listOf<DrawerItem>(
            DrawerItem(R.drawable.ic_dashboard, "Dashboard"),
            DrawerItem(R.drawable.ic_email, "Email", 15),
            DrawerItem(R.drawable.ic_notifications, "Notifications", 20),
            DrawerItem(R.drawable.ic_callendar, "Calendar"),
            DrawerItem(R.drawable.ic_statistics, "Statistics"),
            DrawerItem(R.drawable.ic_settings, "Settings")
        )

        _drawerItemsStateFlow.value = data
    }
}