package com.example.newz;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Articles> newsItems;

    public Adapter(ArrayList<Articles> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    private Context context;
    private OnItemClickListener onItemClickListener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.news_card,parent,false);
        return new MyViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder=holders;
        Articles currentArticles=newsItems.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(currentArticles.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(currentArticles.getTitle());
        holder.desc.setText(currentArticles.getDescription());
        Source source=currentArticles.getSource();
        holder.time.setText(" \u2022 " + Utils.DateFormat(currentArticles.getPublishedAt()));

        holder.source.setText(source.getName());
        if(holder.publishedAt!=null) {
            holder.publishedAt.setText(" \u2022 " + Utils.DateToTimeFormat(currentArticles.getPublishedAt()));
        }
        holder.author.setText(currentArticles.getAuthor());
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title,desc,author,publishedAt,source, time;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView,OnItemClickListener onItemClickListener){
            super(itemView);
            itemView.setOnClickListener(this);

            title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.img);
            progressBar=itemView.findViewById(R.id.progress_load_photo);

            desc=itemView.findViewById(R.id.desc);
            author=itemView.findViewById(R.id.author);
            publishedAt=itemView.findViewById(R.id.published_at);
            source=itemView.findViewById(R.id.source);
            time=itemView.findViewById(R.id.time);
            this.onItemClickListener=onItemClickListener;
        }
        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());

        }
    }
}
