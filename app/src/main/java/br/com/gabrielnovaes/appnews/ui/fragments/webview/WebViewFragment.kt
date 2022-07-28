package br.com.gabrielnovaes.appnews.ui.fragments.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.gabrielnovaes.appnews.databinding.FragmentArticleBinding

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

}