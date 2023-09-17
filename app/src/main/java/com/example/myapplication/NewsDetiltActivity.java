package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetiltActivity extends AppCompatActivity {

     String  title,content,disc,url,img;
     private TextView ndtitle,ndsubtitle,ndcontent;
     private ImageView ndimg;
     private Button ndreadnews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detilt);
        title = getIntent().getStringExtra("title");
       disc = getIntent().getStringExtra("disc");
        content = getIntent().getStringExtra("content");
        img = getIntent().getStringExtra("img");
        url = getIntent().getStringExtra("url");
        ndtitle = findViewById(R.id.ivtitle);
        ndsubtitle = findViewById(R.id.ivsubtitle);
        ndcontent = findViewById(R.id.ivcontent);
        ndimg = findViewById(R.id.ivnews);
        ndreadnews = findViewById(R.id.ivreadnews);

        ndtitle.setText(title);
        ndsubtitle.setText(disc);
        ndcontent.setText(content);
        Picasso.get().load(img).into(ndimg);

        ndreadnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }
}