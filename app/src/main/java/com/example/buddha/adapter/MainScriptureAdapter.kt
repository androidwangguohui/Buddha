package com.example.buddha.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buddha.databinding.ItemMainScriptureBinding

class MainScriptureAdapter(private val fruitList: List<String>, private val listener: ItemClickListener) :
    RecyclerView.Adapter<MainScriptureAdapter.MainScriptureHolder>() {

    @SuppressLint("ClickableViewAccessibility")
    inner class MainScriptureHolder(itemView: ItemMainScriptureBinding) : RecyclerView.ViewHolder(itemView.root) {

        init {
            itemView.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        val imageView: ImageView = itemView.imageView
        val nameTextView: TextView = itemView.nameTextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScriptureHolder {
        val mItemMainScriptureBinding:ItemMainScriptureBinding  = ItemMainScriptureBinding.inflate(LayoutInflater.from(parent.context))
        return MainScriptureHolder(mItemMainScriptureBinding)
    }

    override fun onBindViewHolder(holder: MainScriptureHolder, position: Int) {
        val fruit = fruitList[position]
        holder.nameTextView.text = fruit
    }

    override fun getItemCount() = fruitList.size
}
 fun interface ItemClickListener {
    fun onItemClick(position: Int)
}