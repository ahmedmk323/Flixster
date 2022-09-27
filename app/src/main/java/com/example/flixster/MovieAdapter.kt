package com.example.flixster

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


const val MOVIE_EXTRA="MOVIE_EXTRA"
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

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val overview = itemView.findViewById<TextView>(R.id.overview)
        private val poster = itemView.findViewById<ImageView>(R.id.poster)

        init {
            itemView.setOnClickListener(this)
        }
        fun bind(movie: Movie) {
            title.text = movie.title
            overview.text= movie.overview
            var image : String

            if (land) {
                image = movie.backImageUrl
            }
            else {
                image = movie.posterImageUrl
            }
            Glide.with(context)
                .load(image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
//                    .override(342,Target.SIZE_ORIGINAL)
                .transform(RoundedCorners(30))
                .into(poster)

        }

        override fun onClick(p0: View?) {
            val movie= movies[adapterPosition]
//            Toast.makeText(context,movie.title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA,movie)
            val options = makeSceneTransitionAnimation(context as MainActivity,title,"title")
            context.startActivity(intent, options.toBundle())

        }

    }
}

