package com.example.hp.qalightandroidapp.fragments.aboutus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hp.qalightandroidapp.R;


public class AboutUsFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        WebView myWebView = (WebView) view.findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        myWebView.loadUrl("http://qalight.com.ua/o-nas/qalight-eto/");
        myWebView.setWebViewClient(new WebViewClient());
       /* myWebView.setWebChromeClient(new WebChromeClient() {

            private SpotsDialog dialog;
            //private AnimatedCircleLoadingView animatedCircleLoadingView;
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (dialog == null)
                {
                    dialog = new SpotsDialog(getContext(), R.style.Custom);
                    dialog.show();
                }
                dialog.setMessage(getString(R.string.loading) + " " + String.valueOf(newProgress) + "%");
                if (newProgress == 100){
                    dialog.dismiss();
                    dialog = null;
                }

            }
        });*/





        webSettings.setJavaScriptEnabled(true);
        return view;
    }

    private class MyWebViewClient extends WebViewClient{

    }

}
