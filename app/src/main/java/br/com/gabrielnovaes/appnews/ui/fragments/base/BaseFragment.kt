package br.com.gabrielnovaes.appnews.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import br.com.gabrielnovaes.appnews.repository.NewsRepository

abstract class BaseFragment<viewModel : ViewModel, binding : ViewBinding> : Fragment() {

    protected lateinit var binding: binding
    protected lateinit var viewModel: viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)

        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(viewModelStore, factory).get(getViewModel())
        return binding.root
    }

    abstract fun getViewModel(): Class<viewModel>

    abstract fun getFragmentRepository(): NewsRepository

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): binding

}