package com.jiewen.mercari.timeline.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.jiewen.mercari.BaseViewHolder
import com.jiewen.mercari.R
import com.jiewen.mercari.timeline.model.ProductData
import com.jiewen.mercari.timeline.model.ProductStatusEnum
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.text.NumberFormat
import java.util.*


class TimelineAdapter(productList: List<ProductData>, context: Context?) : RecyclerView.Adapter<BaseViewHolder>() {
    private var productList: List<ProductData> = productList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private val context: Context? = context

    companion object {
        const val TAG = "TimelineAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = mInflater.inflate(R.layout.timeline_cell, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val productData: ProductData = productList[position]

        val holder = viewHolder as ProductViewHolder

        when (ProductStatusEnum.fromValue(productData.status)) {
            ProductStatusEnum.SOLD_OUT -> holder.productStatus.visibility = View.VISIBLE
            else -> holder.productStatus.visibility = View.GONE
        }

        holder.numOfComments.text = productData.num_comments.toString()
        holder.numOfLikes.text = productData.num_likes.toString()
        holder.productName.text = productData.name
        holder.price.text = NumberFormat.getCurrencyInstance(Locale.US).format(productData.price)

        Picasso.get()
            .load(productData.photo)
            .apply {
                placeholder(R.drawable.icon_launcher)
            }
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .into(holder.productImage, object : Callback {
                override fun onSuccess() {
                    android.util.Log.v(TAG, "load image success")
                }

                override fun onError(e: Exception?) {
                    holder.productImage.setImageResource(R.drawable.icon_launcher)
                }
            })
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder internal constructor(itemView: View) : BaseViewHolder(itemView),
        View.OnClickListener {
        internal var numOfLikes: TextView = itemView.findViewById(R.id.num_of_likes)
        internal var numOfComments: TextView = itemView.findViewById(R.id.num_of_comments)
        internal var price: TextView = itemView.findViewById(R.id.price)
        internal var productImage: AppCompatImageView = itemView.findViewById(R.id.product_image)
        internal var productStatus: AppCompatImageView = itemView.findViewById(R.id.status_label)
        internal var productName: TextView = itemView.findViewById(R.id.product_name)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            // TODO: implement onclick action here
            context?.let {
                Toast.makeText(it, it.resources.getString(R.string.timeline_click_toast, productName.text), Toast.LENGTH_SHORT).show()
            }
        }
    }
}