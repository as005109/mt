package com.lisovskaya.lab3.view

import com.arellomobile.mvp.MvpView
import com.lisovskaya.lab3.api.OmdbSearchResult
import com.lisovskaya.lab3.api.OmdbSearchResultMovie

interface ListView: MvpView {
    fun initialState()
    fun showLoading(isLoading: Boolean)
    fun showList(searchResults: List<OmdbSearchResultMovie>)
    fun showIsEmpty(isEmpty: Boolean)
}