package top.linesoft.open2share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        WebView webView = findViewById(R.id.webView);
        webView.loadUrl("https://doc.linesoft.top/open2share/guide/");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(R.string.guide);
    }
}
