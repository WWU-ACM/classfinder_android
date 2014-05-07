package edu.wwu.classfinder2;

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

public class TranscriptFragment
    extends Fragment
    implements ValueCallback<String> {

    private static String TRANSCRIPT_URL =
        "https://admin.wwu.edu/pls/wwis/wwskahst.WWU_ViewTran";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null)
            return null;
        return inflater.inflate(R.layout.load_transcript,
                                container,
                                false);
    }
    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        final ValueCallback<String> mActivity = this;

        final WebView mWebView = (WebView) getView();

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
            Toast.makeText(getActivity(), value,
                           Toast.LENGTH_SHORT).show();

        }
    }

}
