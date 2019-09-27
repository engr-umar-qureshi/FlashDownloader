package com.halfinfinity.flashdownloader.ui.pinboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.halfinfinity.flashdownloader.R
import com.halfinfinity.flashdownloaderlib.FlashDownloader
import com.halfinfinity.pintrestapi.network.models.PinboardItem
import kotlinx.android.synthetic.main.item_pinboard.view.*

class PinboardAdapter(private val context: AppCompatActivity, private val listPinboardItem: ArrayList<PinboardItem>) : RecyclerView.Adapter<PinboardAdapter.PinboardItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinboardItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pinboard, parent, false)
        return PinboardItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PinboardItemViewHolder, position: Int) {
        val fleetItem = listPinboardItem[position]

        if(fleetItem.color.trim() != ""){
            holder.imgPinboard.setBackgroundColor(Color.parseColor(fleetItem.color))
        }

        FlashDownloader.loadImageFrom(context,fleetItem.urlRegular)
            .loadInto {
                holder.imgPinboard.setImageBitmap(it)
            }
    }

    override fun getItemCount(): Int {
        return listPinboardItem.size
    }

    class PinboardItemViewHolder(viewHolder: View) : RecyclerView.ViewHolder(viewHolder) {
        var imgPinboard: ImageView = viewHolder.imgPinboard
    }
}