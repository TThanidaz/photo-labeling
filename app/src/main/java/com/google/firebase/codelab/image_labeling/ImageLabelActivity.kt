package com.google.firebase.codelab.image_labeling

import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler

import kotlinx.android.synthetic.main.activity_image_label.*
import kotlinx.android.synthetic.main.item_row.*

class ImageLabelActivity : BaseCameraActivity() {

    private val itemAdapter: ImageLabelAdapter by lazy {
        ImageLabelAdapter(listOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvLabel.layoutManager = LinearLayoutManager(this)
        rvLabel.adapter = itemAdapter
    }

    override fun onClick(view: View?) {
        progressBar.visibility = View.VISIBLE
        cameraView.captureImage{ cameraKitImage ->
//            runImageLabeling(cameraKitImage.bitmap)
            runCloudImageLabeling(cameraKitImage.bitmap)
            runOnUiThread{
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }
        }
    }

    private fun runImageLabeling(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val imageLabeler = FirebaseVision.getInstance().onDeviceImageLabeler
        imageLabeler.processImage(image)
            .addOnSuccessListener { visionList ->
                progressBar.visibility = View.GONE
                itemAdapter.setList(visionList)
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            .addOnFailureListener{ err ->
                Toast.makeText(baseContext, "Sorry, something went wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun runCloudImageLabeling(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val imageLabeler = FirebaseVision.getInstance().getCloudImageLabeler()
        imageLabeler.processImage(image)
            .addOnSuccessListener { visionList ->
                progressBar.visibility = View.GONE
                itemAdapter.setList(visionList)
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            .addOnFailureListener{ err ->
                Toast.makeText(baseContext, "Sorry, something went wrong",Toast.LENGTH_SHORT).show()
            }

    }

}
