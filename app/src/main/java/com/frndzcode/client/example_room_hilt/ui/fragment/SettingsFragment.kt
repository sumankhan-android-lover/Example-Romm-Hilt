package com.frndzcode.client.example_room_hilt.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.frndzcode.client.example_room_hilt.R
import com.frndzcode.client.example_room_hilt.databinding.FragmentSettingsBinding
import com.frndzcode.client.example_room_hilt.ui.activity.MainActivity
import com.frndzcode.client.example_room_hilt.ui.callbacks.DocumentUploadCallBack
import com.frndzcode.client.example_room_hilt.ui.viewmodel.NewsViewModel
import com.frndzcode.client.example_room_hilt.utils.DataBindingUtils
import com.frndzcode.client.example_room_hilt.utils.custom.facebookHash
import com.frndzcode.client.example_room_hilt.utils.custom.showELog
import com.frndzcode.client.example_room_hilt.utils.custom.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val newsViewModel: NewsViewModel by viewModels()

    //private var getProfilePicture: ExternalDocument? = null
    private var uploadList: ArrayList<File>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtils.putContentView(R.layout.fragment_settings, layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        initListener()
    }

    private fun bindView() {
        requireActivity().facebookHash()
//        getProfilePicture = ExternalDocument(
//            requireActivity(),
//            requireActivity().supportFragmentManager,
//            "pictures",
//            false
//        )
    }

    private fun initListener() {
        binding.facebook.setOnClickListener {
            (requireActivity() as MainActivity).callFacebookLogin { isSuccess: Boolean, loginUsingType: String, socialLoginFName: String, socialLoginLName: String, socialLoginEmail: String, socialLoginImage: String, socialAuthKey: String, socialId: String ->
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

        binding.takePhoto.setOnClickListener {
            selectPhotoType(false)
            //getProfilePicture?.init()
        }

        DocumentUploadCallBack.onGetDocumentData(object : DocumentUploadCallBack.Companion.OnGetDocumentData{
            override fun getGalleryPhotos(data: Intent?) {
                if (data?.clipData != null) {
                    val clipData = data.clipData
                    for (i in 0 until clipData?.itemCount!!) {
                        val uri = clipData.getItemAt(i)?.uri
                        val file: File? = getProfilePicture?.copyUriToInternalPath(uri)
                        if (file != null) {
                            uploadList?.add(file)
                        }
                    }
                } else if (data?.data != null) {
                    val file: File? = getProfilePicture?.copyUriToInternalPath(data.data)
                    if (file?.exists() == true) {
                        uploadList?.add(file)
                        binding.photo.setImageURI(Uri.fromFile(file))
                    }
                }else{
                    "not select photo".showShortToast(requireContext())
                }
                showELog("total list of photo : ${uploadList?.size}")
            }

            override fun getCameraPhotos(capture: File?) {
                if (capture != null) {
                    uploadList?.add(capture)
                    binding.photo.setImageURI(Uri.fromFile(capture))
                }else{
                    "not capture a photo".showShortToast(requireContext())
                }

            }

        })

//        DocumentUploadCallBack.onDocumentClicked(object :
//            DocumentUploadCallBack.Companion.OnDocumentClicked {
//            override fun selectDocument() {
//                getProfilePicture?.selectDocument()
//            }
//
//            override fun selectGalleryDocument() {
//                getProfilePicture?.selectGallery()
//            }
//
//            override fun selectCameraDocument() {
//                getProfilePicture?.selectCamera()
//            }
//
//        })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        showELog("result - $resultCode -- $resultCode -- $data")
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//                PICK_DOCUMENT, PICK_GALLERY -> if (data != null) {
//                    if (data.clipData != null) {
//                        val clipData = data.clipData
//                        for (i in 0 until clipData?.itemCount!!) {
//                            val uri = clipData.getItemAt(i)?.uri
//                            val file: File? = getProfilePicture?.copyUriToInternalPath(uri)
//                            if (file != null) {
//                                uploadList?.add(file)
//                            }
//                        }
//                    } else if (data.data != null) {
//                        val file: File? = getProfilePicture?.copyUriToInternalPath(data.data)
//                        if (file?.exists() == true) {
//                            uploadList?.add(file)
//                            binding.photo.setImageURI(Uri.fromFile(file))
//                        }
//                    }
//                    showELog("upload list size : ${uploadList?.size}")
//                }
//                PICK_CAMERA -> {
//                    showELog("photo : ${getProfilePicture?.imageFile}")
//                    if (getProfilePicture?.imageFile?.exists() == true) {
//                        uploadList?.add(getProfilePicture?.imageFile!!)
//                        binding.photo.setImageURI(Uri.fromFile(getProfilePicture?.imageFile))
//                    }
//                }
//            }
//        }
//    }

}
