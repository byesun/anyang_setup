import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.App


public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        // 2초 후에 b 액티비티로 이동
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // b 액티비티로 전환
                Intent intent = new Intent(AActivity.this, BActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티를 종료
            }
        }, 2000); // 2초 대기 (2000 밀리초)
    }
}

