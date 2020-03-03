package com.sebastianmurgu.nooz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sebastianmurgu.nooz.network.Article
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.article_view.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val articlesViewModel: ArticlesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        article_list.layoutManager = GridLayoutManager(this, 2)

        articlesViewModel.loadPopularArticles()

        articlesViewModel.articles.observe(this, Observer { articles ->
            articles ?: return@Observer
            article_list.adapter = ArticlesAdapter(articles, this)
        })
    }

    private class ArticlesAdapter(val articles: List<Article>, val context: Context): RecyclerView.Adapter<ArticleViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            return ArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.article_view, parent, false))
        }

        override fun getItemCount(): Int {
            return articles.count()
        }

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            val article = articles[position]

            article.media.firstOrNull()?.mediaMetadata?.firstOrNull()?.url.let {
                Glide.with(context).load(it).into(holder.itemView.article_image)
            }

            holder.itemView.article_title.text = article.title
        }
    }

    private class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view)
}


