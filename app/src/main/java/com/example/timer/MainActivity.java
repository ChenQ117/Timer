package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    TextView tv_min;
    TextView tv_sec;
    Button bt_reset;
    final int NUM = 60;
    ConstraintLayout mConstraintLayout;
    boolean isClick = false;
    int temp = NUM;
    boolean running = true;
    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);//系统自带提示音
    Ringtone rt;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
//                    running = true;
                    updateView();
                    mHandler.sendEmptyMessageDelayed(0,1000);
                    break;
                case 1:
                    /*running = false;
                    if (temp == 0){
                        temp = NUM;
                    }
                    tv_sec.setText(String.valueOf(temp));*/
                    mHandler.removeMessages(0);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_min = findViewById(R.id.tv_minite);
        tv_sec = findViewById(R.id.tv_second);
        bt_reset = findViewById(R.id.bt_reset);
        mConstraintLayout = findViewById(R.id.constraintlayout);

        rt = RingtoneManager.getRingtone(getApplicationContext(), uri);

        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                if (isClick){
                    isClick = false;
                    Log.d("isClick",String.valueOf(isClick));
                    message.what = 1;
                    mHandler.sendMessage(message);
                    if (temp == 0){
                        rt.stop();
                        temp = NUM;
                        tv_sec.setText(String.valueOf(temp));
                    }
                }else {
                    isClick = true;
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            }
        });
        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = NUM;
                tv_sec.setText(String.valueOf(temp));
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
                if (isClick){
                    isClick = false;
                }else {
                    isClick = true;
                }
            }
        });
    }
    void updateView(){
        temp--;
        tv_sec.setText(String.valueOf(temp));
        if (temp == 0){
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
            rt.play();
        }

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while (running){
                    try {
                        temp--;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_sec.setText(String.valueOf(temp));
                            }
                        });
                        if (temp == 0){
                            Message message = new Message();
                            message.what = 1;
                            mHandler.sendMessage(message);
                            rt.play();
                        }
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

    }
}
