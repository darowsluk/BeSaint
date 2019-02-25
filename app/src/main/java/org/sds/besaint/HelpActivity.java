package org.sds.besaint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        WebView webView = findViewById(R.id.id_webHelp);
        String langFile = "file:///android_asset/www/" + getResources().getString(R.string.res_langHelpFile);
        webView.loadUrl(langFile);
    }
}
