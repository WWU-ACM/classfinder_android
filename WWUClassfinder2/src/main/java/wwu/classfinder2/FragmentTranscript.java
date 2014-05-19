package edu.wwu.classfinder2;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTranscript extends Fragment {

    private static String TRANSCRIPT_URL = "https://admin.wwu.edu/pls/wwis/wwskahst.WWU_ViewTran";

    class JavaScriptInterface {
        @JavascriptInterface
        public void processHTML(String html) {
            Toast.makeText(getActivity(), html, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void debugHTML(String html) {
            int maxSize = 1000;
            for (int i = 0; i < html.length() / maxSize; i++) {
                int start = i * maxSize;
                int end = (i + 1) * maxSize;
                end = end > html.length() ? html.length() : end;
                Log.d("HTML", html.substring(start, end));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transcript, null);

        Button button = (Button) view.findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = ((EditText) view.findViewById(R.id.username)).getText().toString();
                    String password = ((EditText) view.findViewById(R.id.password)).getText().toString();
                    getTranscript(username, password);
                }
            });

        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void getTranscript(final String username, final String password) {

        final WebView mWebView = new WebView(getActivity());

        mWebView.addJavascriptInterface(new JavaScriptInterface(), "JHTML");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(false);
        webSettings.setSaveFormData(false);

        mWebView.setWebViewClient(new WebViewClient() {

                int tries = 0;

                @Override
                public void onPageFinished(WebView view, String url) {
                    /*
                     * !!!! THIS IS NOT FORWARD-COMPATABLE WITH ANDROID 4.4 (API
                     * !!!! LEVEL 19) IT WILL NOT RUN PROPERLY IF THE TARGET API
                     * !!!! LEVEL IS SET TO 19 (OR HIGHER)
                     */
                    if (tries < 1) {
                        mWebView.loadUrl("javascript:document.getElementById('username').value = '" + username + "';");
                        mWebView.loadUrl("javascript:document.getElementById('pwd').value = '" + password + "';");
                        mWebView.loadUrl("javascript:document.getElementsByTagName('input')[6].click();");
                        tries++;
                    }
                    if (url.equals(TRANSCRIPT_URL)) {
                        mWebView.loadUrl("javascript:JHTML.processHTML(document.documentElement.outerHTML);");
                    } else {
                        mWebView.loadUrl("javascript:JHTML.debugHTML(document.documentElement.outerHTML);");
                    }
                }
            });

        mWebView.loadUrl(TRANSCRIPT_URL);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }
}
