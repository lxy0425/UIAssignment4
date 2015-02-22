package com.example.apple.threaddatabase;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by apple on 15-01-18.
 */
public class WebActivity extends FragmentActivity{
    WebView webView;
    Button back;
    int count = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        String name = "";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl("https://www.google.ca/search?site=&source=hp&ei=qkm7VLaOOtb-yQSD8YGIBA&q="+name);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
