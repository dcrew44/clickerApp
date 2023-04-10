package com.example.clickerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    public Cookie cookie;
    public LayoutInflater inflater;
    public ConstraintLayout container;
    public TextView cookieCount;
    public ImageView cookieImage;
    public Looper looper;
    public Thread backgroundThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookie = new Cookie();
        cookieCount = findViewById(R.id.clickCount);
        cookieCount.setText(cookie.getClicks());
        container = findViewById(R.id.container);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        cookieImage = (ImageView) inflater.inflate(R.layout.cookie_image, null);
        container.addView(cookieImage, 0);
        cookieImage.setOnClickListener(v -> {
            cookie.click();
            handler.sendEmptyMessage(1);
        });
        backgroundThread = new Thread(update);
        backgroundThread.start();
        looper = Looper.getMainLooper();
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    cookie.update();
                    Thread.sleep(cookie.getAutoClickRate());
                    handler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
            }
            cookieCount.setText(cookie.getClicks());
        }
    };

}