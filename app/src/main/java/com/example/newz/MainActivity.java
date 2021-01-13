package com.example.newz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Activity selectedActivity;

    private ArrayList<SourceParent> sourceParentArrayList;



    NewsApi newsApi;


    private ExampleAdapter exampleAdapter;
    private BottomNavigationView navigationView;

    private ArrayList<Articles> articlesArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        navigationView=findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(navListener);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        newsApi = retrofit.create(NewsApi.class);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        articlesArrayList = new ArrayList<>();
        sourceParentArrayList=new ArrayList<>();

        getArticles();

    }
    BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedActivity=null;

            switch (item.getItemId()){
                case R.id.nav_sources:selectedActivity=new SourceActivity();
                break;
            }
            Intent intent =new Intent(MainActivity.this,SourceActivity.class);
            startActivity(intent);
            return true;
        }
    };

    private void getArticles() {


        Map<String, String> parameters=new HashMap<>();

        parameters.put("country","us");
        parameters.put("apiKey","51489d0921204a4897faf023a056a1b1");

        Call<Parent> call=newsApi.getNewsArticles(parameters);

        call.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                    if (!response.isSuccessful()) {
                        Log.d("TAG", "onResponse:"+"code: " + response.code());
                        return;
                    }

                Parent parentItem=response.body();

                ArrayList<Articles> parentArrayList=parentItem.getArticles();

                for(Articles article:parentArrayList){
                    String author=article.getAuthor();
                    String title=article.getTitle();
                    String publishedAt=article.getPublishedAt();
                    String urlToImagearticle=article.getUrlToImage();
                    Source source=article.getSource();
                    String description=article.getDescription();
                    String webUrl=article.getUrl();

                }

                exampleAdapter=new ExampleAdapter(MainActivity.this,parentArrayList);
                recyclerView.setAdapter(exampleAdapter);
                Log.d("TAG", "onResponse: "+parentItem.getStatus());
                Log.d("TAG", "onResponse: "+parentArrayList);

            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });



    }




}