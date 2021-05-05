package nz.co.conglomo.nprc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Website extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void handleIntent(Intent intent) {

        // Set up the web view
        WebView myWebView = findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Handle the intent
        String url = "https://nprc.nz/";
        if (intent != null) {
            String action = intent.getAction();
            Uri data = intent.getData();
            if (action != null
                    && action.equals(Intent.ACTION_VIEW)
                    && data != null) {
                url = data.toString();
            }
        }

        // Default to the home page
        myWebView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri != null && uri.getHost() != null && "nprc.nz".equals(uri.getHost())) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            } else {
                // Otherwise, the link is not for a page on my site,
                // so launch another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }
    }
}
