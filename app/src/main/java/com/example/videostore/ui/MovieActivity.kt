package com.example.videostore.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videostore.R
import com.example.videostore.room.entity.Category
import com.example.videostore.room.entity.Movie
import com.example.videostore.ui.adapters.CategoryListAdapter
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private lateinit var adapter: CategoryListAdapter
    private lateinit var viewModel: VideoStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setupList()
        setupViewModel()
        saveButton.setOnClickListener {

            val movie = Movie(
                0,
                editTextName.text.toString(),
                editTextAuthor.text.toString())

            viewModel.saveMovie(
                movie,
                assignCategoriesByView())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_movie, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert_categories -> {
                viewModel.seedCategoriesData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupList(){
        adapter = CategoryListAdapter(this)
        categoryrecyclerview.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        categoryrecyclerview.layoutManager = layoutManager
    }

    private fun setupViewModel(){

        viewModel = ViewModelProvider
            .AndroidViewModelFactory(application)
            .create(VideoStoreViewModel::class.java)

        viewModel.allCategories.observe(this, Observer { data ->
            data?.let {
                adapter.updateData(
                    it,
                    assignCategoriesByViewModel()
                )
            }
        })

        viewModel.currentMovie.observe(this, Observer { data ->
            data?.let {
                editTextName.setText(it.name)
                editTextAuthor.setText(it.author)
                setupAssignCategories(it.id)
            }
        })

        val movieId = intent.getLongExtra(MOVIE_ID,0)
        if(movieId > 0){
            viewModel.setCurrentMovie(movieId)
        }
    }

    private fun setupAssignCategories(movieId: Long){
        if(viewModel.currentAssignCategories == null){
            viewModel.setCurrentAssignCategories(movieId)
            viewModel.currentAssignCategories?.observe(this, Observer {
                adapter.updateData(allCategories(),it )
            })
        }
    }

    private fun allCategories(): List<Category> {
        viewModel.allCategories.value?.run {
            return this
        }
        return emptyList()
    }

    private fun assignCategoriesByViewModel(): List<Category>? {
        viewModel.currentAssignCategories?.let {
            return it.value
        }
        return null
    }

    private fun assignCategoriesByView():  List<Category> {
        val assignCategories: MutableList<Category> = mutableListOf()
        adapter.data.forEach{
            if(it.checked){
                assignCategories.add(it.category)
            }
        }
        return assignCategories.toList()
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        @JvmStatic
        fun newInstance(context: Context, movieId:Long?): Intent {
            val intent = Intent(context, MovieActivity::class.java)
            movieId?.let {
                intent.putExtra(MOVIE_ID,it)
            }
            return  intent
        }
    }
}
