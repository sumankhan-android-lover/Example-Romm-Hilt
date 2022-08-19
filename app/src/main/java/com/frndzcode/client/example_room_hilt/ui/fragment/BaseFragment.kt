package com.frndzcode.client.example_room_hilt.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frndzcode.client.example_room_hilt.R
import com.frndzcode.client.example_room_hilt.app.MyConstants.STATIC_OBJ.PICK_CAMERA
import com.frndzcode.client.example_room_hilt.app.MyConstants.STATIC_OBJ.PICK_DOCUMENT
import com.frndzcode.client.example_room_hilt.app.MyConstants.STATIC_OBJ.PICK_GALLERY
import com.frndzcode.client.example_room_hilt.ui.activity.BaseActivity
import com.frndzcode.client.example_room_hilt.ui.callbacks.DocumentUploadCallBack
import com.frndzcode.client.example_room_hilt.utils.custom.showELog
import com.frndzcode.client.example_room_hilt.utils.helper.ExternalDocument

open class BaseFragment : Fragment() {
    private lateinit var baseActivity: BaseActivity
    var getProfilePicture: ExternalDocument? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            baseActivity = requireActivity() as BaseActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        initListener()
    }

    private fun bindView() {

    }

    private fun initListener() {
        DocumentUploadCallBack.onDocumentClicked(object :
            DocumentUploadCallBack.Companion.OnDocumentClicked {
            override fun selectDocument() {
                getProfilePicture?.selectDocument()
            }

            override fun selectGalleryDocument() {
                getProfilePicture?.selectGallery()
            }

            override fun selectCameraDocument() {
                getProfilePicture?.selectCamera()
            }

        })
    }

    fun selectPhotoType(selectMultiple:Boolean){
        getProfilePicture = ExternalDocument(
            requireActivity(),
            requireActivity().supportFragmentManager,
            "pictures",
            selectMultiple
        )
        getProfilePicture?.init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_DOCUMENT, PICK_GALLERY -> if (data != null) {
                    DocumentUploadCallBack.onGetDocumentData.getGalleryPhotos(data)
                }
                PICK_CAMERA -> {
                    if (getProfilePicture?.imageFile?.exists() == true) {
                        DocumentUploadCallBack.onGetDocumentData.getCameraPhotos(getProfilePicture?.imageFile)
                    }
                }
            }
        }
    }

    companion object {

    }
}