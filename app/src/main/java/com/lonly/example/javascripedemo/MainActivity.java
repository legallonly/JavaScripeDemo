package com.lonly.example.javascripedemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private Button mButtonMovement;
    private WebView mWebView;
    private String mStartRandomMoveJavaScript;
    private String mStopRandomMoveJavaScript;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int x = msg.arg1;
            int y = msg.arg2;

            mButtonMovement.layout(x, y,mButtonMovement.getMeasuredWidth() + x, mButtonMovement.getMeasuredHeight() + y);
        }
    };

    /**
     * 映射对象
     * 里面的方法必须使用@JavascriptInterface注解
     */
    class MyObject{
        @JavascriptInterface
        public void move(int x, int y){
            Message msg = new Message();
            msg.arg1 = x;
            msg.arg2 = y;
            handler.sendMessage(msg);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonMovement = findViewById(R.id.button3);
        mWebView = new WebView(this);
        WebSettings mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        //第一个参数：要映射的对象  第二哥参数：映射对象的名字
        mWebView.addJavascriptInterface(new MyObject(),"demo");
        try {
            InputStream is = getResources().openRawResource(R.raw.start_radom_move);
            byte[] buffer = new byte[1024];

            int count = is.read(buffer);
            mStartRandomMoveJavaScript = new String(buffer, 0, count, "utf-8");

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //得到资源中的Raw数据流
            InputStream is = getResources().openRawResource(R.raw.stop_radom_move);
            //得到数据的大小
            int length = is.available();
            byte[] buffer = new byte[length];
            //读取数据
            is.read(buffer);
            mStopRandomMoveJavaScript = new String(buffer, 0, length, "utf-8");
            //关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onClick_Start(View view){
        mWebView.loadDataWithBaseURL(null,mStartRandomMoveJavaScript, "text/html", "utf-8", null);
    }
    public void onClick_Stop(View view){
        mWebView.loadDataWithBaseURL(null,mStopRandomMoveJavaScript, "text/html", "utf-8", null);
    }
}
