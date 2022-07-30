package br.com.gabrielnovaes.appnews.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrielnovaes.appnews.R
import br.com.gabrielnovaes.appnews.data.local.db.ArticleDatabase
import br.com.gabrielnovaes.appnews.data.remote.RetrofitInstance
import br.com.gabrielnovaes.appnews.databinding.FragmentFavoriteBinding
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import br.com.gabrielnovaes.appnews.ui.adapter.MainAdapter
import br.com.gabrielnovaes.appnews.ui.fragments.base.BaseFragment
import br.com.gabrielnovaes.appnews.util.state.ArticleListEvent
import br.com.gabrielnovaes.appnews.util.state.ArticleListState
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment:
    BaseFragment<FavoriteViewModel, FragmentFavoriteBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dispatch(ArticleListEvent.Fetch)
        setupRecyclerView()
        observerResults()
    }

    private fun observerResults(){
        viewModel.favorite.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ArticleListState.Success -> {
                    binding.rvProgressBarFavorite.visibility = View.INVISIBLE
                    response.list?.let { _->
                        mainAdapter.differ.submitList(response.list)
                    }
                }
                is ArticleListState.Loading -> {
                    binding.rvProgressBarFavorite.visibility = View.VISIBLE
                }
                is ArticleListState.ErrorMessage -> {
                    binding.rvProgressBarFavorite.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(),"Ocorreu um erro ${response.message}", Toast.LENGTH_SHORT).show()
                }

                is ArticleListState.Empty -> {
                    mainAdapter.differ.submitList(emptyList())
                    binding.tvListEmpty.visibility = View.VISIBLE
                    binding.rvProgressBarFavorite.visibility = View.INVISIBLE
                }
            }
        }
    }


    private fun setupRecyclerView()  = with(binding.rvFavorite){
        this.adapter = mainAdapter
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        val itemTouchPerCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,

            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = mainAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(viewHolder.itemView, "Delete with success", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction(getString(R.string.undo)) {
                            viewModel.saveArticle(article)
                            mainAdapter.notifyDataSetChanged()
                        }
                        show()
                    }

            }
        }

        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        mainAdapter.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }

    }

    override fun getViewModel(): Class<FavoriteViewModel> = FavoriteViewModel::class.java

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, db = ArticleDatabase.invoke(requireContext()))

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)


}

