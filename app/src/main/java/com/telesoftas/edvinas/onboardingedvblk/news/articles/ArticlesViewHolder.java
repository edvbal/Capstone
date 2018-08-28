package com.telesoftas.edvinas.onboardingedvblk.news.articles;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.utils.DateFormatter;
import com.telesoftas.edvinas.onboardingedvblk.utils.ImageManager;

import butterknife.BindView;
import butterknife.ButterKnife;

class ArticlesViewHolder extends RecyclerView.ViewHolder {
    public static final int NOUGAT_VERSION = 24;
    private final NewsItemClickListener itemClickListener;
    @BindView(R.id.parentNewsItemLayout)
    LinearLayout parentNewsItemLayout;
    @BindView(R.id.newsItemImage)
    ImageView imageViewNewsImage;
    @BindView(R.id.newsItemTitleText)
    TextView textViewNewsTitle;
    @BindView(R.id.newsItemDateText)
    TextView textViewNewsDate;
    private final ImageManager imageManager;
    private final DateFormatter dateFormatter;

    public ArticlesViewHolder(
            View itemView,
            ImageManager imageManager,
            NewsItemClickListener itemClickListener,
            DateFormatter dateFormatter
    ) {
        super(itemView);
        this.itemClickListener = itemClickListener;
        this.dateFormatter = dateFormatter;
        this.imageManager = imageManager;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Article article) {
        Article articleWithFormattedDate = new Article(article, getFormattedDate(article));
        imageManager.loadImageFromUrl(imageViewNewsImage, article.getUrlToImage());
        setTitle(article.getTitle());
        textViewNewsDate.setText(articleWithFormattedDate.getDate());
        parentNewsItemLayout.setOnClickListener(
                View -> itemClickListener.onNewsItemClick(articleWithFormattedDate));
    }

    private void setTitle(String title) {
        if (Build.VERSION.SDK_INT >= NOUGAT_VERSION) {
            textViewNewsTitle.setText(Html.fromHtml(title,
                    Html.FROM_HTML_MODE_LEGACY));
        } else {
            //noinspection deprecation
            textViewNewsTitle.setText(Html.fromHtml(title));
        }
    }

    private String getFormattedDate(Article article) {
        return dateFormatter.changeStringDateFormat(article.getDate());
    }
}
