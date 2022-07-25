package br.com.gabrielnovaes.appnews.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrielnovaes.appnews.R
import br.com.gabrielnovaes.appnews.adapter.MainAdapter
import br.com.gabrielnovaes.appnews.databinding.ActivityFavoriteBinding
import br.com.gabrielnovaes.appnews.model.Article
import br.com.gabrielnovaes.appnews.model.data.NewsDataSource
import br.com.gabrielnovaes.appnews.presenter.ViewHome
import br.com.gabrielnovaes.appnews.presenter.favorite.FavoritePresenter
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: ActivityFavoriteBinding

    private val mainAdapter by lazy {
        MainAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)
        presenter.getAll()
        setLayout()
        openArticle()

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
                presenter.deleteArticle(article)
                Snackbar.make(viewHolder.itemView, "Delete with success", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction(getString(R.string.undo)) {
                            presenter.saveArticle(article)
                            mainAdapter.notifyDataSetChanged()
                        }
                        show()
                    }

            }
    }

        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        presenter.getAll()
    }

    override fun showArticles(article: List<Article>) {
        mainAdapter.differ.submitList(article.toList())
    }

    private fun setLayout() {
        with(binding.rvFavorite) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun openArticle() {
        mainAdapter.setOnClickListener {
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
    }

}