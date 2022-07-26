package br.com.gabrielnovaes.appnews.ui.activities

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import br.com.gabrielnovaes.appnews.data.local.model.Article
import br.com.gabrielnovaes.appnews.databinding.FragmentArticleBinding
import br.com.gabrielnovaes.appnews.presenter.ViewHome
import br.com.gabrielnovaes.appnews.presenter.favorite.FavoritePresenter
import br.com.gabrielnovaes.appnews.repository.NewsDataSource
import com.google.android.material.snackbar.Snackbar

class ArticleActivity : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: FragmentArticleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        intent.extras.let {
            it?.get("article").let { articleSend ->
                article = articleSend as Article
            }
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }

        binding.fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(it, "Article saved success!! ", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showArticles(article: List<Article>) {}


}