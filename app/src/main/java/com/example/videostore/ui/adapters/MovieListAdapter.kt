package com.example.videostore.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videostore.R
import com.example.videostore.room.entity.Movie

class MovieListAdapter  internal constructor(
    context: Context,
    val listener: MovieListener) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data: MutableList<Movie> = mutableListOf()

    internal fun updateData(items: List<Movie>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = data[position]
        holder.name.text =  "${current.name} ${current.id}"
        holder.author.text =  "${current.author} ${current.id}"

        holder.itemView.setOnClickListener{
            it.tag = current
            listener.onSelected(current)
        }
    }

    override fun getItemCount() = data.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val author: TextView = itemView.findViewById(R.id.author)
    }

    interface MovieListener {
        fun onSelected(movie:Movie)
    }
}