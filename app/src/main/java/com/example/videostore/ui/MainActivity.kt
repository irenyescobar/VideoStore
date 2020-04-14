package com.example.videostore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videostore.R
import com.example.videostore.room.entity.Movie
import com.example.videostore.ui.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieListAdapter.MovieListener {

    private lateinit var adapter: MovieListAdapter
    private lateinit var viewModel: VideoStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupList()
        setupViewModel()

        fab.setOnClickListener { _ ->
            startActivity(MovieActivity.newInstance(this,null))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert_movies -> {
                viewModel.seedMoviesData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupList(){
        adapter = MovieListAdapter(this,this)
        recyclerview.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.addItemDecoration(DividerItemDecoration(
            recyclerview.context,
            layoutManager.orientation
        ))
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider
            .AndroidViewModelFactory(application)
            .create(VideoStoreViewModel::class.java)

        viewModel.allMovies.observe(
            this,
            Observer { data ->
               data?.let { adapter.updateData(it) }
            }
        )
    }

    override fun onSelected(movie: Movie) {
        startActivity(MovieActivity.newInstance(this,movie.id))
    }
}
