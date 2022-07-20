package com.frndzcode.client.example_room_hilt.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.frndzcode.client.example_room_hilt.R
import com.frndzcode.client.example_room_hilt.databinding.FragmentSettingsBinding
import com.frndzcode.client.example_room_hilt.ui.activity.MainActivity
import com.frndzcode.client.example_room_hilt.ui.viewmodel.NewsViewModel
import com.frndzcode.client.example_room_hilt.utils.DataBindingUtils
import com.frndzcode.client.example_room_hilt.utils.custom.facebookHash
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val newsViewModel : NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtils.putContentView(R.layout.fragment_settings, layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        initListener()
    }

    private fun bindView() {
        requireActivity().facebookHash()
    }

    private fun initListener() {
        binding.facebook.setOnClickListener {
            (requireActivity() as MainActivity).callFacebookLogin { isSuccess: Boolean, loginUsingType: String, socialLoginFName: String, socialLoginLName: String, socialLoginEmail: String, socialLoginImage: String, socialAuthKey: String,socialId:String ->
                if (isSuccess) {
                    newsViewModel.fName.set(socialLoginFName)
                    newsViewModel.lName.set(socialLoginLName)
                    newsViewModel.userEmail.set(socialLoginEmail)
                    newsViewModel.userPhoto.set(socialLoginImage)
                    newsViewModel.socialId.set(socialId)
                    newsViewModel.socialType.set(loginUsingType)
                    newsViewModel.setSocialMediaUserData()
                }
            }
        }

        binding.google.setOnClickListener {

        }

        binding.linkedin.setOnClickListener {

        }
    }

}
