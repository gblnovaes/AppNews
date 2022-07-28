package br.com.gabrielnovaes.appnews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrielnovaes.appnews.databinding.ItemNewsBinding
import br.com.gabrielnovaes.appnews.data.local.model.Article
import com.bumptech.glide.Glide

class MainAdapter : RecyclerView.Adapter<MainAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url === newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
                Glide.with(holder.itemView.context).load(this.urlToImage)
                    .into(binding.ivArticleImage)
                binding.tvTitle.text = this.title
                binding.tvDescription.text = this.author ?: this.source?.name
                binding.tvPublishedAt.text = this.publishedAt
                binding.tvSource.text = this.source?.name

                binding.containerItem.setOnClickListener {
                    onItemClickedListener?.let { article ->
                        article(this)
                    }
                }
            }
        }
    }

    private var onItemClickedListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickedListener = listener
    }
}