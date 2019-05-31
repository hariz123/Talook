package id.talook;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DetailActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView tvDataReceived;
    ImageView postImage;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvDataReceived = (TextView) findViewById(R.id.detailTV);
        postImage = (ImageView)findViewById(R.id.imgsrc);
        String name = getIntent().getStringExtra("id");
        String text = "" + name;

        Document document = Jsoup.parse(text);
        tvDataReceived.setText(document.text());
        Elements elements = document.select("img");
        Glide.with(this)
                .load(elements.get(0).attr("src"))
                .apply(new RequestOptions().override(350, 550))
                .into(postImage);
//        tvDataReceived.setText(text);
        progressBar.setVisibility(View.GONE);

    }
}
