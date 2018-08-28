package com.telesoftas.edvinas.onboardingedvblk.news.articles;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.utils.DateFormatter;
import com.telesoftas.edvinas.onboardingedvblk.utils.ImageManager;

import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesViewHolder> {
    private final List<Article> articles = new ArrayList<>();
    private final ImageManager imageManager;
    private final NewsItemClickListener itemClickListener;
    private final DateFormatter dateFormatter;

    public ArticlesAdapter(
            ImageManager imageManager,
            NewsItemClickListener itemClickListener,
            DateFormatter dateFormatter
    ) {
        super();
        this.imageManager = imageManager;
        this.itemClickListener = itemClickListener;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_recycler_news, parent, false);
        return new ArticlesViewHolder(itemView, imageManager, itemClickListener, dateFormatter);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void clearArticles() {
        articles.clear();
        notifyDataSetChanged();
    }

    public void setArticles(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    public List<Article> getArticles() {
        return articles;
    }
}
