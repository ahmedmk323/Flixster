package com.example.flixster

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val NOW_PLAYING_URL=
    "https://api.themoviedb.org/3/movie/now_playing?api_key=bc7a39a4659e9f7b0710e5cdbf153dba"
private const val TAG= "MainActivity"
private const val TAG2= "Orientation"

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.review_icon)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        rvMovies = findViewById(R.id.rvMovies)
        val orientation = resources.configuration.orientation
        var isLand = false
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            isLand = false
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLand = true
        }
        val movieAdapter = MovieAdapter(this, movies, isLand)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)
        val client = AsyncHttpClient()

        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON data $json")
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie List $movies")
                } catch (e: JSONException){
                    Log.e(TAG, "Encountered exception $e")
                }
            }

        })
    }

}