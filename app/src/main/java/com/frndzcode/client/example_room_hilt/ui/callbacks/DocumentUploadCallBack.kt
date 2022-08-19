package com.frndzcode.client.example_room_hilt.ui.callbacks

import android.content.Intent
import java.io.File

class DocumentUploadCallBack {
    companion object {
        lateinit var onDocumentClicked: OnDocumentClicked
        fun onDocumentClicked(mDocumentClicked: OnDocumentClicked) {
            onDocumentClicked = mDocumentClicked
        }

        interface OnDocumentClicked {
            fun selectDocument()
            fun selectGalleryDocument()
            fun selectCameraDocument()
        }

        lateinit var onGetDocumentData :OnGetDocumentData
        fun onGetDocumentData(mDocumentData: OnGetDocumentData) {
            onGetDocumentData = mDocumentData
        }

        interface OnGetDocumentData {
            fun getGalleryPhotos(data:Intent?)
            fun getCameraPhotos(capture:File?)
        }
    }
}