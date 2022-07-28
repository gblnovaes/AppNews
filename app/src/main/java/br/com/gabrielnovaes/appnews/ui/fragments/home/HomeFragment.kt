package br.com.gabrielnovaes.appnews.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gabrielnovaes.appnews.data.local.db.ArticleDatabase
import br.com.gabrielnovaes.appnews.data.remote.RetrofitInstance
import br.com.gabrielnovaes.appnews.databinding.FragmentHomeBinding
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import br.com.gabrielnovaes.appnews.ui.adapter.MainAdapter
import br.com.gabrielnovaes.appnews.ui.fragments.base.BaseFragment
import br.com.gabrielnovaes.appnews.util.state.StateResource

class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observerResults()
    }

    private fun observerResults(){
        viewModel.getAll.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.rvProgressBar.visibility = View.INVISIBLE
                    response.data?.let { data->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }
                }
                is StateResource.Loading -> {
                    binding.rvProgressBar.visibility = View.VISIBLE
                }
                is StateResource.Error -> {
                    binding.rvProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(),"Ocorreu um erro",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView()  = with(binding){
        rvNews.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
            )
        }
        mainAdapter.setOnClickListener {
          val action =   HomeFragmentDirections.actionHomeFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getFragmentRepository(): NewsRepository  = NewsRepository(RetrofitInstance.api,
        ArticleDatabase.invoke(requireContext()))

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)
}