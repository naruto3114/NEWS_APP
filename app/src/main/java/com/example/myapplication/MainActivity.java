package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategorClickInterface {

// 38900cfa579c41d49c3c617a63bb5389
      private RecyclerView newsRV,categoryRV;
      private ProgressBar loadingPB;
      private ArrayList<Articles> articlesArrayList;
      private ArrayList<CategoryRVModal> categoryRVModalArrayList;
      private CategoryRVAdapter categoryRVAdapter;
      private NewsRVAdapter newsRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategoris);
        loadingPB = findViewById(R.id.PBloding);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::oncategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategory();
        getNews("ALL");
        newsRVAdapter.notifyDataSetChanged();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.BLACK_SHADE_1));
        }

    }
    private void getCategory(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://media.istockphoto.com/id/909582208/photo/laptop-screen-showing-latest-online-news.webp?b=1&s=170667a&w=0&k=20&c=ccRqZHrJDc98oaXpEuaqcw5JV5ghiDcEeggxKGStPng="));
        categoryRVModalArrayList.add(new CategoryRVModal("science","https://images.unsplash.com/photo-1507413245164-6160d8298b31?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2NpZW5jZXxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("sport","https://media.istockphoto.com/id/1424617746/photo/targeting-the-business-concept-businessman-touch-red-arrow-dart-to-the-virtual-target.webp?b=1&s=170667a&w=0&k=20&c=mAcVF3r5o2CZi3B6VNoIuNa_1ZZ5gNYDZDw8QFYGfEc="));
        categoryRVModalArrayList.add(new CategoryRVModal("health","https://images.unsplash.com/photo-1477332552946-cfb384aeaf1c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aGVhbHRofGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60"));




        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String CategoryURL = "v2/top-headlines?country=in&category="+category + "&apikey=38900cfa579c41d49c3c617a63bb5389";

        String url = "v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortby=publishedAt&language=en&apikey=38900cfa579c41d49c3c617a63bb5389";

         String BASE_url = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if(category.equalsIgnoreCase("All")){
            call = retrofitAPI.getAllNews(url);

        }else {
            call = retrofitAPI.getNewsByCategoris(CategoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
               NewsModel newsModel = response.body();
               loadingPB.setVisibility(View.GONE);
               ArrayList<Articles> articles = newsModel != null? newsModel.getArticles(): new ArrayList<>();
               for (int i=0; i<articles.size(); i++){
                   articlesArrayList.add(new Articles(articles.get(i).getTitle(),
                           articles.get(i).getDescription(),
                           articles.get(i).getUrlToImage(),
                           articles.get(i).getUrl(),
                           articles.get(i).getContent()));
               }
               newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void oncategoryClick(int position) {
    String category = categoryRVModalArrayList.get(position).getCategory();
    getNews(category);
    }
}