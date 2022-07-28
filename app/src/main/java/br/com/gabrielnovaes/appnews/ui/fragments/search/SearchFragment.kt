package br.com.gabrielnovaes.appnews.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gabrielnovaes.appnews.data.local.db.ArticleDatabase
import br.com.gabrielnovaes.appnews.data.remote.RetrofitInstance
import br.com.gabrielnovaes.appnews.databinding.FragmentSearchBinding
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import br.com.gabrielnovaes.appnews.ui.adapter.MainAdapter
import br.com.gabrielnovaes.appnews.ui.fragments.base.BaseFragment

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding) {
        rvNewsSearch.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
        mainAdapter.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun getViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getFragmentRepository(): NewsRepository = NewsRepository(
        RetrofitInstance.api,
        ArticleDatabase.invoke(requireContext())
    )

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding  = FragmentSearchBinding.inflate(inflater,container,false
    )
}