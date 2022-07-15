package com.frndzcode.client.example_room_hilt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.frndzcode.client.example_room_hilt.R
import com.frndzcode.client.example_room_hilt.databinding.FragmentHomeBinding
import com.frndzcode.client.example_room_hilt.ui.viewmodel.NewsViewModel
import com.frndzcode.client.example_room_hilt.utils.DataBindingUtils.Companion.putContentView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = putContentView(R.layout.fragment_home, layoutInflater, container)
        return binding.root
    }

}