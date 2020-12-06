package com.lisovskaya.lab3.view

import com.arellomobile.mvp.MvpView
import com.lisovskaya.lab3.api.OmdbSearchResult
import com.lisovskaya.lab3.api.OmdbSearchResultMovie

interface FavoritesListView: MvpView {
    fun showList(searchResults: List<OmdbSearchResultMovie>)
    fun showIsEmpty(isEmpty: Boolean)
}