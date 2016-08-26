package com.example.scxh.giveme;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * 系统通常是在执行了onPause()与onStop() 之后再调用onDestroy() ，
     * 除非你的程序在onCreate()方法里面就调用了finish()方法，。
     * 在某些情况下，例如你的activity只是做了一个临时的逻辑跳转的功能，
     * 它只是用来决定跳转到哪一个activity，这样的话，你需要在onCreate里面去调用finish方法，
     * 这样系统会直接就调用onDestory方法，其它生命周期的方法则不会被执行。
     */
    public Handler mhandler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mhandler = new Handler(){
            public void handleMessage(Message message){
                int type = message.arg1;
                switch (type){
                    case 0:
                        Intent intent = new Intent(MainActivity.this,PagerActivity.class);
                        startActivity(intent);
                        finish();// TODO: 2016/6/12 finish()方法保证退出时不再进入当前页面
                        break;

                }
            }
        };

        Message msg = Message.obtain();
        msg.arg1 = 0;
        mhandler.sendMessageDelayed(msg,30);// TODO: 2016/6/12 以延迟的方式传递内容，（内容，延迟时间）


    }
}


