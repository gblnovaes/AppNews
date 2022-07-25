package br.com.gabrielnovaes.appnews.ui

import android.webkit.WebViewClient
import br.com.gabrielnovaes.appnews.R
import br.com.gabrielnovaes.appnews.model.Article
import br.com.gabrielnovaes.appnews.model.data.NewsDataSource
import br.com.gabrielnovaes.appnews.presenter.ViewHome
import br.com.gabrielnovaes.appnews.presenter.favorite.FavoritePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AbstractActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    override fun getLayout() = R.layout.activity_article

    override fun onInject() {
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        intent.extras.let {
            it?.get("article").let { articleSend ->
                article = articleSend as Article
            }
        }

        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }

        fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(it, "Article saved success!! ", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showArticles(article: List<Article>) {}


}