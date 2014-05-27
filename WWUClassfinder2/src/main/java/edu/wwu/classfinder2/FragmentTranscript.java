package edu.wwu.classfinder2;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
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

    public FragmentTranscript() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transcript, container, false);

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

    public void getTranscript(final String username, final String password) {

        final WebView mWebView = new WebView(getActivity());

        mWebView.addJavascriptInterface(new JavaScriptInterface(), "JHTML");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(false);
        webSettings.setSaveFormData(false);

        mWebView.setWebViewClient(new WebViewClient() {

            int tries = 0;

            // TODO: THIS IS VULNERABLE TO XSS ATTACKS!!
            @Override
            public void onPageFinished(WebView view, String url) {
                    /*
                     * !!!! THIS IS NOT FORWARD-COMPATABLE WITH ANDROID 4.4 (API
                     * !!!! LEVEL 19) IT WILL NOT RUN PROPERLY IF THE TARGET API
                     * !!!! LEVEL IS SET TO 19 (OR HIGHER)
                     *
                     * Or it might work and uh, I just had the wrong code. Leave it at 19 and see what breaks.
                     * Google DID change WebView code in API 19, so this code can definitely break at any time.
                     */
                if (tries < 1) {
                    mWebView.loadUrl("javascript:document.getElementById('username').value = '" + username + "';" +
                            "javascript:document.getElementById('pwd').value = '" + password + "';" +
                            "javascript:document.getElementsByTagName('input')[6].dispatchEvent(new MouseEvent('click'));");
                    tries++;
                }
                if (url.equals(TRANSCRIPT_URL)) {
                    mWebView.loadUrl("javascript:JHTML.processHTML(document.documentElement.outerHTML);");
                } else {
                    mWebView.loadUrl("javascript:JHTML.debugHTML(document.documentElement.outerHTML);");
                    // TODO: Just show a failed dialog here
                }
            }
        });

        mWebView.loadUrl(TRANSCRIPT_URL);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }
}
