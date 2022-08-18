package com.frndzcode.client.example_room_hilt.utils.helper

import android.Manifest
import com.frndzcode.client.example_room_hilt.ui.dialog.BottomSheetUploadDocument
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import android.content.Intent
import android.provider.MediaStore
import android.app.Activity
import android.content.Context
import android.provider.OpenableColumns
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.frndzcode.client.example_room_hilt.BuildConfig
import com.frndzcode.client.example_room_hilt.app.MyConstants.STATIC_OBJ.CHECK_PERMISSIONS
import com.frndzcode.client.example_room_hilt.app.MyConstants.STATIC_OBJ.PICK_CAMERA
import com.frndzcode.client.example_room_hilt.app.MyConstants.STATIC_OBJ.PICK_DOCUMENT
import com.frndzcode.client.example_room_hilt.app.MyConstants.STATIC_OBJ.PICK_GALLERY
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ExternalDocument(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    folder: String?,
    allowMultiple: Boolean
) {
    private var bottomSheet: BottomSheetUploadDocument? = null
    private var systemFolder: String? = null
    var imageFile: File? = null
    private val allowMultiple: Boolean
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private val bitmap: Bitmap? = null
    fun init() {
        checkPermissions()
    }

    private fun createImageFile(): Uri {
        val folder = File(context.getExternalFilesDir(null), systemFolder)
        if (folder != null && !folder.exists()) folder.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "image_" + timeStamp + "_.jpg"
        imageFile = File(folder, imageFileName)
        return FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID + ".provider",
            imageFile!!
        )
    }

    fun selectCamera() {
        bottomSheet!!.dismiss()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uri = createImageFile()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        (context as Activity).startActivityForResult(intent, PICK_CAMERA)
    }

    fun selectDocument() {
        bottomSheet!!.dismiss()
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        if (allowMultiple) intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        (context as Activity).startActivityForResult(intent, PICK_DOCUMENT)
    }

    fun selectGallery() {
        bottomSheet!!.dismiss()
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        if (allowMultiple) intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        (context as Activity).startActivityForResult(intent, PICK_GALLERY)
    }

    fun copyUriToInternalPath(uri: Uri?): File? {
        val returnCursor = context.contentResolver.query(uri!!, null, null, null, null)
        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        try {
            val inputStream = context.contentResolver.openInputStream(
                uri
            )
            val outputFile = createStorageFile(returnCursor.getString(nameIndex))
            val outputStream: OutputStream = FileOutputStream(outputFile)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream!!.read(buf).also { len = it } > 0) {
                outputStream.write(buf, 0, len)
            }
            return outputFile
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun createStorageFile(name: String): File {
        val folder = File(context.getExternalFilesDir(null), "documents")
        if (folder != null && !folder.exists()) folder.mkdirs()
        return File(folder, name)
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    (context as Activity),
                    Manifest.permission.CAMERA
                )
            ) {
                showPermissionAlert()
            } else {
                ActivityCompat.requestPermissions(context, permissions, CHECK_PERMISSIONS)
            }
        } else if (ContextCompat.checkSelfPermission(
                (context as Activity),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                showPermissionAlert()
            } else {
                ActivityCompat.requestPermissions(context, permissions, CHECK_PERMISSIONS)
            }
        } else handleNext()
    }

    fun checkPermissionResult(grantResults: IntArray) {
        var flag = 1
        for (i in grantResults.indices) {
            if (grantResults[i] == -1) flag = 0
        }
        if (flag == 1) handleNext() else showPermissionAlert()
    }

    private fun showPermissionAlert() {
        AlertDialog.Builder(context)
            .setTitle("Permission Requested")
            .setMessage("Please allow all permissions to continue current operation")
            .setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                    (context as Activity),
                    permissions,
                    CHECK_PERMISSIONS
                )
            }
            .setNegativeButton("No") { dialog, which -> dialog.dismiss() }
            .setCancelable(false)
            .create().show()
    }

    private fun handleNext() {
        bottomSheet = BottomSheetUploadDocument()
        bottomSheet!!.show(fragmentManager, "UploadSheet")
    }

    init {
        systemFolder = folder
        this.allowMultiple = allowMultiple
    }
}