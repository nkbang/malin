package org.changeip.seoul.malin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    //웹뷰용으로 삽입
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(false == isConnected()){
            Toast.makeText(this, "인터넷이 연결되지 않았습니다. 이 FMB 앱은 인터넷 연결이 필요합니다.", Toast.LENGTH_LONG).show();
            return;
        }
        switch ( getNetworkType() ){
            case ConnectivityManager.TYPE_WIFI:
                Toast.makeText(this, "WIFI를 사용하시는군요. ", Toast.LENGTH_SHORT).show();

                HttpMgrThread httpThread = new HttpMgrThread();
                httpThread.start();
                break;

            case ConnectivityManager.TYPE_MOBILE:
                Toast.makeText(this, "모바일 데이터를 연결하셨군요.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        //웹뷰용으로 삽입
        myWebView = findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://fs26.formsite.com/nkbang/form3/");
        myWebView.setWebViewClient(new WebViewClient());
        // 삽입 끝
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }



    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetWork != null && activeNetWork.isConnectedOrConnecting();


        return isConnected;

    }

    private int getNetworkType(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();

        return activeNetWork.getType();

    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
