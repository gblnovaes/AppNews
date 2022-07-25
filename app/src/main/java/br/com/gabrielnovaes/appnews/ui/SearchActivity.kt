package br.com.gabrielnovaes.appnews.ui

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gabrielnovaes.appnews.R
import br.com.gabrielnovaes.appnews.adapter.MainAdapter
import br.com.gabrielnovaes.appnews.model.Article
import br.com.gabrielnovaes.appnews.model.data.NewsDataSource
import br.com.gabrielnovaes.appnews.presenter.ViewHome
import br.com.gabrielnovaes.appnews.presenter.search.SearchPresenter
import br.com.gabrielnovaes.appnews.util.UtilQueryTextListener
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AbstractActivity(),ViewHome.View {

    private val mainAdapter by lazy{
        MainAdapter()
    }

    private lateinit var presenter:SearchPresenter

    override fun getLayout() = R.layout.activity_search

    override fun onInject() {
        val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this,dataSource)
        setLayout()
        search()
        openArticle()
    }

   private fun setLayout(){
        with(rvNewsSearch){
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

    private fun search(){
        searchNews.setOnQueryTextListener(UtilQueryTextListener(this.lifecycle) { newText ->
            newText?.let { query ->
                if (query.isNotEmpty()) {
                     presenter.search(query)
                    rvProgressBarSearch.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun showProgressBar() {
        rvProgressBarSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
     }

    override fun hideProgressBar() {
        rvProgressBarSearch.visibility = View.INVISIBLE
    }

    override fun showArticles(article: List<Article>) {
       mainAdapter.differ.submitList(article.toList())
    }

    private fun openArticle() {
        mainAdapter.setOnClickListener {
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article",it)
            startActivity(intent)
        }
    }

}