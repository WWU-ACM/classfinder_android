package edu.wwu.classfinder2;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;

public class ActivityMain
    extends Activity implements ValueCallback<String> {

    private static String TRANSCRIPT_URL =
        "https://admin.wwu.edu/pls/wwis/wwskahst.WWU_ViewTran";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ValueCallback<String> mActivity = this;

        final WebView mWebView =
            (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (url.equals(TRANSCRIPT_URL)) {
                        mWebView.evaluateJavascript("document.getElementsByTagName('html')[0].innerHTML", mActivity);
                    }
                }
            });

        mWebView.loadUrl(TRANSCRIPT_URL);
    }

    public void onReceiveValue(String value) {
        if (value != null) {
            Toast.makeText(this, value,
                           Toast.LENGTH_SHORT).show();

        }
    }

}
