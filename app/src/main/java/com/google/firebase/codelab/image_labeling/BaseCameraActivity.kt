package com.google.firebase.codelab.image_labeling

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_image_label.*

abstract class BaseCameraActivity : AppCompatActivity(), View.OnClickListener {
    protected val TAG = BaseCameraActivity::class.java.name

    val sheetBehavior by lazy {
        BottomSheetBehavior.from(layout_bottom_sheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,"onCreate...")
        setContentView(R.layout.activity_image_label)
        btnRetry.setOnClickListener {
            if(cameraView.visibility == View.VISIBLE) showPreview()
            else hidePreview()
        }
        fab_take_photo.setOnClickListener(this)
        sheetBehavior.peekHeight = 224
    }

    override fun onResume() {
        super.onResume()
        cameraView?.start()
    }

    override fun onPause() {
        cameraView?.stop()
        super.onPause()
    }

    protected fun showPreview() {
        framePreview.visibility = View.VISIBLE
        cameraView?.visibility = View.GONE
    }
    protected fun hidePreview() {
        framePreview.visibility = View.GONE
        cameraView?.visibility = View.VISIBLE
    }
}