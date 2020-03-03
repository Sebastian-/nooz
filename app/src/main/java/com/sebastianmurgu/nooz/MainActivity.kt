package com.sebastianmurgu.nooz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sebastianmurgu.nooz.network.Article
import com.sebastianmurgu.nooz.network.NewsAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.article_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val api = NewsAPI()
    private var articles: List<Article> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        article_list.layoutManager = GridLayoutManager(this, 2)

        loadPopularArticles()
    }

    private fun loadPopularArticles() = GlobalScope.launch(Dispatchers.Main) {
        val popularArticles = api.getPopularArticles()
        if(popularArticles != null) {
            articles = popularArticles
            update()
        }
    }

    private fun update() {
        article_list.adapter = ArticlesAdapter(articles, this)
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

            holder.itemView.article_title.text = article.title
        }
    }

    private class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view)
}


