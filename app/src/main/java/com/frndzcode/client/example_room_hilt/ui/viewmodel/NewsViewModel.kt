package com.frndzcode.client.example_room_hilt.ui.viewmodel

import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frndzcode.client.example_room_hilt.data.model.article.NewsResponse
import com.frndzcode.client.example_room_hilt.data.network.Resource
import com.frndzcode.client.example_room_hilt.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val appPrefs: SharedPreferences
) :  ViewModel(){

    //social media login
    var userEmail = ObservableField("")
    var fName = ObservableField("")
    var lName = ObservableField("")
    var userPhoto = ObservableField("")
    var socialId = ObservableField("")
    var socialType = ObservableField("")




    private val _getBreakingNewsResponse: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val getBreakingNewsResponse: LiveData<Resource<NewsResponse>>
        get() = _getBreakingNewsResponse

    fun getBreakingNews(
    ) = viewModelScope.launch {
        _getBreakingNewsResponse.value = Resource.Loading
        _getBreakingNewsResponse.value = repository.getBreakingNews()
    }

    fun setSocialMediaUserData() = viewModelScope.launch  {
        appPrefs.edit()
            .putString("f_name", fName.get())
            .putString("l_name", lName.get())
            .putString("user_email", userEmail.get())
            .putString("user_photo", userPhoto.get())
            .putString("social_id", socialId.get())
            .putString("social_type", socialType.get())
            .putBoolean("login",true)
            .apply()
    }


}