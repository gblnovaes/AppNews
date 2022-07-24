package br.com.gabrielnovaes.appnews.ui

import android.webkit.WebViewClient
import br.com.gabrielnovaes.appnews.R
import br.com.gabrielnovaes.appnews.model.Article
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AbstractActivity() {

    private lateinit var article: Article

    override fun getLayout()  = R.layout.activity_article

    override fun onInject() {

        intent.extras.let {
            it?.get("article").let { articleSend ->
                article = articleSend as Article
            }
        }

        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url->
                loadUrl(url)
            }
        }
    }
}