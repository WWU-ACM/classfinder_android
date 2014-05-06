package edu.wwu.classfinder2;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;

public class ActivityMain extends Activity {

    private static String TRANSCRIPT_URL =
        "https://admin.wwu.edu/pls/wwis/wwskahst.WWU_ViewTran";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView mWebView = (WebView) findViewById(R.id.activity_main_webview);
        final Activity mActivity = this;
        mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (url.equals(TRANSCRIPT_URL)) {
                        Toast.makeText(mActivity, "Got the transcript",
                                       Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, "Got this page"+url,
                                       Toast.LENGTH_SHORT).show();
                    }
                }
            });

        mWebView.loadUrl(TRANSCRIPT_URL);
    }
}
