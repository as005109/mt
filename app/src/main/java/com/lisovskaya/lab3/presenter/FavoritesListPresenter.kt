package com.lisovskaya.lab3.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lisovskaya.lab3.api.OmdbApi
import com.lisovskaya.lab3.api.OmdbSearchResultMovie
import com.lisovskaya.lab3.db.MovieDao
import com.lisovskaya.lab3.view.FavoritesListView
import com.lisovskaya.lab3.view.ListView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

@InjectViewState
class FavoritesListPresenter(private val omdbDao: MovieDao?) : MvpPresenter<FavoritesListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        omdbDao?.let {
            Single.create<List<OmdbSearchResultMovie>> { emiter ->
                SingleOnSubscribe<List<OmdbSearchResultMovie>> { single ->
                    val list = it.getAllSavedMovies()
                    single.onSuccess(list)
                }
                    .subscribe(emiter)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data, error ->
                    if(data.isNotEmpty()) {
                        viewState.showIsEmpty(false)
                        viewState.showList(data)
                    }
                    else {
                        viewState.showIsEmpty(true)
                    }
                }
        }
    }
}