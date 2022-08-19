package com.frndzcode.client.example_room_hilt.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frndzcode.client.example_room_hilt.R
import com.frndzcode.client.example_room_hilt.databinding.FragmentBottomSheetUploadDocumentBinding
import com.frndzcode.client.example_room_hilt.ui.callbacks.DocumentUploadCallBack
import com.frndzcode.client.example_room_hilt.utils.DataBindingUtils.Companion.putContentView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetUploadDocument : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetUploadDocumentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = putContentView(R.layout.fragment_bottom_sheet_upload_document, layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            documentUpload.setOnClickListener {
               DocumentUploadCallBack.onDocumentClicked.selectDocument()
            }

            cameraUpload.setOnClickListener {
                DocumentUploadCallBack.onDocumentClicked.selectCameraDocument()
            }

            galleryUpload.setOnClickListener {
                DocumentUploadCallBack.onDocumentClicked.selectGalleryDocument()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BottomSheetUploadDocument.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomSheetUploadDocument().apply {
                arguments = Bundle().apply {

                }
            }
    }
}