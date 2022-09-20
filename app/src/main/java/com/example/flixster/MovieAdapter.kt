package com.example.flixster

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

class MovieAdapter(private val context: Context, private val movies: List<Movie>,
                   private val land: Boolean) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int =  movies.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val overview = itemView.findViewById<TextView>(R.id.overview)
        private val poster = itemView.findViewById<ImageView>(R.id.poster)

        fun bind(movie: Movie) {
            title.text = movie.title
            overview.text= movie.overview
            if (land) {
                Glide.with(context)
                    .load(movie.backImageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .override(780,Target.SIZE_ORIGINAL)
                    .into(poster)
            }
            else {
                Glide.with(context)
                    .load(movie.posterImageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .override(342,Target.SIZE_ORIGINAL)
                    .into(poster)
            }

        }

    }
}

