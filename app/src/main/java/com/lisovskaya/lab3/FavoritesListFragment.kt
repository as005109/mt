package com.lisovskaya.lab3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.lisovskaya.lab3.api.OmdbSearchResultMovie
import com.lisovskaya.lab3.presenter.FavoritesListPresenter
import com.lisovskaya.lab3.view.FavoritesListView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favorites_list.*

class FavoritesListFragment : MvpAppCompatFragment(), FavoritesListView {

    @InjectPresenter
    lateinit var presenter: FavoritesListPresenter

    private lateinit var listAdapter: MovieListAdapter

    @ProvidePresenter
    fun providePresenter(): FavoritesListPresenter {
        return FavoritesListPresenter((activity?.application as? App)?.omdbDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_favorites_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as? App)?.let { app ->
            listAdapter = MovieListAdapter(
                emptyList(),
                (activity?.application as? App)?.omdbDao,
                requireActivity() as MovieListAdapter.MyItemClickListener
            )
            recyclerView.adapter = listAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun showList(searchResults: List<OmdbSearchResultMovie>) {
        emptyTextView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        listAdapter.setNewListOfItems(searchResults)
    }

    override fun showIsEmpty(isEmpty: Boolean) {
        if(isEmpty) {
            recyclerView.visibility = View.INVISIBLE
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
    }
}