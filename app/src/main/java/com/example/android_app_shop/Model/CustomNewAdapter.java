package com.example.android_app_shop.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_app_shop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomNewAdapter extends BaseAdapter {
    List<News> newsList;
    Context context;
    LayoutInflater layoutInflater;

    public CustomNewAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        if (i >= 0 && i < newsList.size()) {
            return newsList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_news, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageNews = convertView.findViewById(R.id.imgAvatar);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDate = convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        News news = newsList.get(i);
        viewHolder.tvTitle.setText(news.getTitle());
        viewHolder.tvDate.setText((CharSequence) news.getDate());
        if (!news.getImageURL().isEmpty()) {
            Picasso.get().load(news.getImageURL()).into(viewHolder.imageNews);
        } else {
//            viewHolder.imgProduct.setImageResource(R.drawable.default_image);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imageNews;
        TextView tvTitle;
        TextView tvDate;
    }
}
