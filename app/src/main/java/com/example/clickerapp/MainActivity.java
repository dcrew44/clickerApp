package com.example.clickerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
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

    public Button upgradeClicker;
    public Button upgradeAutoClicker;
    public Button addAutoClicker;
    public TextView upgradeCost;
    public TextView autoClickerCost;
    public TextView autoClickerUpgradeCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookie = new Cookie();

        cookieCount = findViewById(R.id.clickCount);
        cookieCount.setText(cookie.getClicks());
        upgradeClicker = (Button) findViewById(R.id.clickUpgradeButton);
        upgradeAutoClicker = (Button) findViewById(R.id.upgradeAutoButton);
        addAutoClicker = (Button) findViewById(R.id.cursorBuyButton);
        upgradeCost = (TextView) findViewById(R.id.upgradeCostText);
        autoClickerCost = (TextView) findViewById(R.id.aBuyText);
        autoClickerUpgradeCost = (TextView) findViewById(R.id.aUpgradeText);

        upgradeClicker.setOnClickListener(v -> {
            if (cookie.upgradeClicker()) {
                upgradeCost.setText("Cost: "+cookie.getUpgradeCost());
            }
        });
        upgradeAutoClicker.setOnClickListener(v -> {
            if (cookie.upgradeAutoClicker()) {
                autoClickerUpgradeCost.setText("Cost: "+cookie.getAutoClickerUpgradeCost());
            }
        });
        addAutoClicker.setOnClickListener(v -> {
            if (cookie.addAutoClicker()) {
                autoClickerCost.setText("Cost: "+cookie.getAutoClickerCost());
                newAutoClicker();
            }
        });
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
    //add mini auto clicker inflating a cursor image around the cookie
    private void newAutoClicker() {
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
                cookieImage.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(500)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                cookieImage.animate()
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .setDuration(500)
                                        .setInterpolator(new AccelerateDecelerateInterpolator())
                                        .start();
                            }
                        })
                        .start();
            }
            cookieCount.setText(cookie.getClicks());
        }
    };

}