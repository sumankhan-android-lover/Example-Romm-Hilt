package com.frndzcode.client.example_room_hilt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frndzcode.client.example_room_hilt.data.network.Resource
import com.frndzcode.client.example_room_hilt.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) :  ViewModel(){

    private val _countryListResponse: MutableLiveData<Resource<CountryResponse>> = MutableLiveData()
    val countryListResponse: LiveData<Resource<CountryResponse>>
        get() = _countryListResponse

    fun getCountryList(
    ) = viewModelScope.launch {
        _countryListResponse.value = Resource.Loading
        _countryListResponse.value = repository.getAllCountryList()
    }

}