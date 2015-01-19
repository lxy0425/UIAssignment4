package com.example.apple.threaddatabase;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
public class FragmentWeb extends Fragment implements View.OnClickListener{
    WebView webView;
    ArrayList<String> name2 = null;
    Button back;
    int count = 1;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateView(inflater,viewGroup,savedInstanceState);
        return inflater.inflate(R.layout.web, viewGroup, false);
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        back = (Button)getView().findViewById(R.id.back);
        back.setOnClickListener(this);
        name2 = ((ConstantsBrowser)getActivity()).getName1();
        webView = (WebView)getView().findViewById(R.id.webView);
        webView.loadUrl("https://www.google.ca/search?site=&source=hp&ei=qkm7VLaOOtb-yQSD8YGIBA&q="+name2.get(0));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    public void onClick(View view){
            if(count < name2.size()) {
                webView.loadUrl("https://www.google.ca/search?site=&source=hp&ei=qkm7VLaOOtb-yQSD8YGIBA&q=" + name2.get(count++));
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }
        if(count == name2.size()){
            startActivity(new Intent(getActivity(),ConstantsBrowser.class));
        }
    }

}
