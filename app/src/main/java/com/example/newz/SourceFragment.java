package com.example.newz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SourceFragment extends Fragment {

    private RecyclerView recyclerView;

    private ArrayList<Bitmap> sourceBitmap= new ArrayList<>();

    Bitmap bmp;

    NewsApi newsApi2;
    NewsApi newsApi;
    ArrayList<Bitmap> object;

    private SourceAdapter sourceAdapter;

    private ArrayList<Bitmap> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sources_main, container, false);
        recyclerView=view.findViewById(R.id.recycler_view_sources);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit_g_api=new Retrofit.Builder()
                .baseUrl("https://www.google.com/s2/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsApi2 = retrofit_g_api.create(NewsApi.class);

        newsApi = retrofit.create(NewsApi.class);


        getSources();
    }

    private void getSources() {


        String apiKey = "51489d0921204a4897faf023a056a1b1";

        Call<SourceParent> call = newsApi.getNewsSources(apiKey);


        call.enqueue(new Callback<SourceParent>() {
            @Override
            public void onResponse(Call<SourceParent> call, Response<SourceParent> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", "onResponse source activity:" + "code: " + response.code());
                    return;
                }

                SourceParent parentItem = response.body();

                ArrayList<Source> sourceArrayList = parentItem.getSources();

//                SourceImageParse sourceImageParse=parentItem.getSourceImageParse();
//                if(sourceImageParse!=null) {
//                    Bitmap ImageFile = sourceImageParse.getSourceImage();
//
//                }

                int index=0;
                for (Source source : sourceArrayList) {
                    String name = source.getName();
                    String country = source.getCountry();
                    String description = source.getDescription();
                    String language = source.getLanguage();
                    String webUrl = source.getUrl();

                    getImage(webUrl,index);
                    index++;
                }


                sourceAdapter = new SourceAdapter(getContext(), sourceArrayList, sourceBitmap);
                recyclerView.setAdapter(sourceAdapter);

                Log.d("TAG", "onResponse: " + parentItem.getStatus());
                Log.d("TAG", "onResponse: " + sourceArrayList);


            }

            @Override
            public void onFailure(Call<SourceParent> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

    }

    private Bitmap getImage(String webUrl,int itemIndex) {


        Log.d("inside bitmap", "getImageFile: " + webUrl);
        Call<ResponseBody> call_source = newsApi2.getImage(64,webUrl);

        call_source.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.d("not Success", "onResponse: " + response.code());
                }
                bmp= BitmapFactory.decodeStream(response.body().byteStream());
                Log.d("bitmap", "onResponse: " + bmp.toString());
                sourceBitmap.add(bmp);
                sourceAdapter.addTheImageAtPosition(bmp,itemIndex);

                Log.d("source bitmap", "onResponse: " + sourceBitmap);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("on failure", "onFailure: " + t.getMessage());

            }
        });

        return bmp;

    }

}
