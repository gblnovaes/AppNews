package br.com.gabrielnovaes.appnews.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gabrielnovaes.appnews.data.local.model.Article
import br.com.gabrielnovaes.appnews.databinding.FragmentSearchBinding
import br.com.gabrielnovaes.appnews.presenter.ViewHome
import br.com.gabrielnovaes.appnews.presenter.search.SearchPresenter
import br.com.gabrielnovaes.appnews.repository.NewsDataSource
import br.com.gabrielnovaes.appnews.ui.adapter.MainAdapter
import br.com.gabrielnovaes.appnews.util.UtilQueryTextListener

class SearchActivity : AppCompatActivity(), ViewHome.View {


    private val mainAdapter by lazy {
        MainAdapter()
    }

    private lateinit var presenter: SearchPresenter
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this, dataSource)
        setLayout()
        search()
        openArticle()
    }

    private fun setLayout() {
        with(binding.rvNewsSearch) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun search() {
        binding.searchNews.setOnQueryTextListener(UtilQueryTextListener(this.lifecycle) { newText ->
            newText?.let { query ->
                if (query.isNotEmpty()) {
                    presenter.search(query)
                    binding.rvProgressBarSearch.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun showProgressBar() {
        binding.rvProgressBarSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideProgressBar() {
        binding.rvProgressBarSearch.visibility = View.INVISIBLE
    }

    override fun showArticles(article: List<Article>) {
        mainAdapter.differ.submitList(article.toList())
    }

    private fun openArticle() {
        mainAdapter.setOnClickListener {
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
    }

}