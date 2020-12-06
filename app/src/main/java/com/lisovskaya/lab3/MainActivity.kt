package com.lisovskaya.lab3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatActivity
import com.lisovskaya.lab3.api.OmdbSearchResultMovie

class MainActivity : MvpAppCompatActivity(), MovieListAdapter.MyItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) return
        val fragment = ListFragment.newInstance("")
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFrame, fragment)
            .commit()
    }

    override fun onMyItemClick(selectedMovie: OmdbSearchResultMovie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, DetailsFragment.newInstance(selectedMovie.title))
            .addToBackStack(null)
            .commit()
    }

    override fun addToFavorites(selectedMovie: OmdbSearchResultMovie) {
        (application as? App)?.let { app ->
            Thread {
                if (app.omdbDao.getMovieById(selectedMovie.imdbId) != null) {
                    app.omdbDao.removeFromFavorites(selectedMovie)
                } else {
                    app.omdbDao.addMovieToFavorites(selectedMovie)
                }
            }.start()
        }
    }

    fun goToFavorites() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, FavoritesListFragment())
            .addToBackStack(null)
            .commit()
    }
}