package com.lisovskaya.lab3.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lisovskaya.lab3.api.OmdbApi
import com.lisovskaya.lab3.view.ListView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@InjectViewState
class ListPresenter(private val omdbApi: OmdbApi?) : MvpPresenter<ListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initialState()
    }

    fun search(searchText: String) {
        viewState.showLoading(true)
        omdbApi?.let {
            it.getResultsForString(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.showLoading(false) }
                .subscribe { results, error ->
                    if (error != null || results.results.isEmpty()) {
                        viewState.showIsEmpty(true)
                    } else {
                        viewState.showList(results.results)
                    }
                }
        }
    }
}