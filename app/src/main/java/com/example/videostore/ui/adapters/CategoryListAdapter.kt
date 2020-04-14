package com.example.videostore.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videostore.R
import com.example.videostore.room.entity.Category

class CategoryListAdapter internal constructor(
    context: Context) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var data: MutableList<CategoryViewModel> = mutableListOf()

    internal fun updateData(allCategories: List<Category>, assignCategories: List<Category>?) {
        data.clear()
        allCategories.forEach {
            var checked = false
            assignCategories?.run {
                checked = any{ el-> el.id == it.id}
            }
            data.add(CategoryViewModel(it, checked))
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = inflater.inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = data[position]
        holder.categoryName.text =  "${current.category.name} ${current.category.id}"
        holder.checkBox.isChecked = current.checked

        holder.itemView.setOnClickListener {
            current.checked = !current.checked
            holder.checkBox.isChecked = current.checked
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = data.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }

    data class CategoryViewModel(val category: Category , var checked: Boolean = false)
}