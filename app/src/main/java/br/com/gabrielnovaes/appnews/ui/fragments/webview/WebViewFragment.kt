package br.com.gabrielnovaes.appnews.ui.fragments.webview

import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.gabrielnovaes.appnews.R
import br.com.gabrielnovaes.appnews.data.local.db.ArticleDatabase
import br.com.gabrielnovaes.appnews.data.local.model.Article
import br.com.gabrielnovaes.appnews.data.remote.RetrofitInstance
import br.com.gabrielnovaes.appnews.databinding.FragmentArticleBinding
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import br.com.gabrielnovaes.appnews.ui.fragments.base.BaseFragment

class WebViewFragment : BaseFragment<WebViewModel, FragmentArticleBinding>() {

    private val args : WebViewFragmentArgs by navArgs()

    private lateinit var article: Article


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url -> loadUrl(url) }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_article ->{
                viewModel.saveArticle(article)
                Toast.makeText(requireContext(),"Article saved success!",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun getViewModel(): Class<WebViewModel> = WebViewModel::class.java

    override fun getFragmentRepository(): NewsRepository = NewsRepository(
        RetrofitInstance.api,
        ArticleDatabase.invoke(requireContext())
    )

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentArticleBinding = FragmentArticleBinding.inflate(inflater,container,false)
}