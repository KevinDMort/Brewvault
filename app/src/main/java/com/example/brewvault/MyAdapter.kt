package com.example.brewvault

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter<T>(
    private var items: List<T>,
    private val onItemClicked: (position: Int) -> Unit)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_card, viewGroup, false)
        return MyViewHolder(view, onItemClicked)
    }
    fun updateItems(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.textView.text = items[position].toString()
    }

    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textView: TextView = itemView.findViewById(R.id.textview_list_item)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}