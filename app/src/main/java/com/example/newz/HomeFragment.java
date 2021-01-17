package com.example.newz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    //todo remove the extra lines and spaces from code base, make code clean
    private RecyclerView recyclerView;
    private Activity selectedActivity;
    private ArrayList<SourceParent> sourceParentArrayList;
    //todo implement bottom navigation with right way, refer some blogs


    NewsApi newsApi;

    //todo add viewmodel

    private ExampleAdapter exampleAdapter;
    private BottomNavigationView navigationView;

    private ArrayList<Articles> articlesArrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        newsApi = retrofit.create(NewsApi.class);


        articlesArrayList = new ArrayList<>();
        sourceParentArrayList = new ArrayList<>();

        getArticles();


    }


    private void getArticles() {


        Map<String, String> parameters = new HashMap<>();

        parameters.put("country", "us");
        parameters.put("apiKey", "51489d0921204a4897faf023a056a1b1");

        Call<Parent> call = newsApi.getNewsArticles(parameters);

        call.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", "onResponse:" + "code: " + response.code());
                    return;
                }

                Parent parentItem = response.body();

                articlesArrayList= parentItem.getArticles();

                exampleAdapter = new ExampleAdapter(getContext(), articlesArrayList);
                recyclerView.setAdapter(exampleAdapter);
                exampleAdapter.notifyDataSetChanged();
                initListener();




            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });


    }

    private void initListener(){

        exampleAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String urlToarticle;
                String title;
                String imageUrl;
                String publishedAt;
                String author;
                String description;
                String date;
                String content;
                String articleUrl;
                Articles currentArticles=articlesArrayList.get(position);
                description=currentArticles.getDescription();
                date=currentArticles.getPublishedAt();
                author=currentArticles.getAuthor();
                title=currentArticles.getTitle();
                content=currentArticles.getContent();
                imageUrl=currentArticles.getUrlToImage();
                articleUrl=currentArticles.getUrl();

                Intent intent=new Intent(getContext(),NewsDetailActivity.class);
                intent.putExtra("url", articleUrl);
                intent.putExtra("title",title );
                intent.putExtra("img",imageUrl  );
                intent.putExtra("date", date);
                intent.putExtra("author",  author);
                startActivity(intent);
            }
        });

    }

}
