package com.hien.ketnoiviet.Others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hien.ketnoiviet.R;

public class NewsDetail extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
       // ánh xa WebView tin tức
        webView = findViewById(R.id.chitiet_tintuc);

        Intent intent = getIntent();
        // lấy link bài viết từ NewsAdapter chuyển sang
        String link = intent.getStringExtra("linkNews");
     // thực hiện load cái link đó va hien thi ra trang web cua bai viet
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}