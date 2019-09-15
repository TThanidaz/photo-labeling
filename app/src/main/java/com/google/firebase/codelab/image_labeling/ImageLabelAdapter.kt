package com.google.firebase.codelab.image_labeling

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import kotlinx.android.synthetic.main.item_row.view.*

class ImageLabelAdapter(private var firebaseVisionList: List<Any>): RecyclerView.Adapter<ImageLabelAdapter.ItemHolder>() {

    lateinit var context: Context

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindDevice(currentItem: FirebaseVisionImageLabel) {
            itemView.itemName.text = currentItem.text
            itemView.itemAccuracy.text = "Accuracy: ${(currentItem.confidence*100).toInt()} %"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false))
    }

    override fun getItemCount(): Int {
        return firebaseVisionList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentItem = firebaseVisionList[position]
        holder.bindDevice(currentItem as FirebaseVisionImageLabel)
    }

    fun setList(visionList: List<Any>) {
        firebaseVisionList = visionList
        notifyDataSetChanged()
    }

}