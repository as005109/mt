package com.lisovskaya.lab3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.lisovskaya.lab3.api.OmdbSearchResultMovie
import com.lisovskaya.lab3.presenter.ListPresenter
import com.lisovskaya.lab3.view.ListView
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : MvpAppCompatFragment(), ListView {

    @InjectPresenter
    lateinit var presenter: ListPresenter

    private var argument1: String? = null
    private lateinit var listAdapter: MovieListAdapter

    @ProvidePresenter
    fun providePresenter(): ListPresenter {
        return ListPresenter((activity?.application as? App)?.omdbApi)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argument1 = arguments?.getString(ARG_PARAM1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = MovieListAdapter(
            emptyList(),
            (activity?.application as? App)?.omdbDao,
            requireActivity() as MovieListAdapter.MyItemClickListener
        )
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchButton.setOnClickListener {
            presenter.search(searchText.text.toString())
        }
        goToFavoritesButton.setOnClickListener {
            (requireActivity() as MainActivity).goToFavorites()
        }

    }

    companion object {
        private const val ARG_PARAM1 = "argument parameter 1"

        @JvmStatic
        fun newInstance(param1: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun initialState() {
        recyclerView.visibility = View.INVISIBLE
        emptyTextView.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    override fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            recyclerView.visibility = View.INVISIBLE
            emptyTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
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