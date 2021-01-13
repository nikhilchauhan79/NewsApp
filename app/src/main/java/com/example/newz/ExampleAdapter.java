package com.example.newz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context context;
    private ArrayList<Articles> newsItems;
    Intent intent;
    Activity selectedActivity;


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_card_activity,parent,false);
        return new ExampleViewHolder(view);
    }

    public ExampleAdapter(Context context, ArrayList<Articles> exampleItems) {
        this.context = context;
        this.newsItems = exampleItems;
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Articles currentArticles=newsItems.get(position);

        String description=currentArticles.getDescription();
        String date=currentArticles.getPublishedAt();
        String author=currentArticles.getAuthor();
        String title=currentArticles.getTitle();
        String content=currentArticles.getContent();

        String imageUrl=currentArticles.getUrlToImage();
        String articleUrl=currentArticles.getUrl();

        holder.description.setText(description);
        holder.dateAndTime.setText(date);
        holder.title.setText(title);
        if(author!=null){
            holder.author.setText(author);
        }

        holder.content.setText(content);

        Picasso.get()
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView description;
        public TextView source;
        public TextView author;
        private ImageView imageView;

        public BottomNavigationView navigationView;

        public TextView content;

        public TextView dateAndTime;

        public ExampleViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.text_view_title);
            description=itemView.findViewById(R.id.text_view_description);
            source=itemView.findViewById(R.id.text_view_source_name);
            dateAndTime=itemView.findViewById(R.id.text_view_date);
            author=itemView.findViewById(R.id.text_view_author);

            content=itemView.findViewById(R.id.text_view_content);

            navigationView=itemView.findViewById(R.id.bottom_navigation);

            imageView=itemView.findViewById(R.id.image_view);

        }



    }


}


