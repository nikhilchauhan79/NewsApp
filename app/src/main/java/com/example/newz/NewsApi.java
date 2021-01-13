package com.example.newz;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NewsApi {

    @GET("top-headlines")
    Call<Parent>getNewsArticles(@QueryMap Map<String, String>parameters);

    @GET("sources")
    Call<SourceParent>getNewsSources(@Query("apiKey")String key);

    @GET("favicons")
    Call<ResponseBody>getImage(
                               @Query("sz")int size,
                               @Query("domain_url")String domain_url
                               );
}
