package com.example.administrator.myapplication;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ImageView iv3=(ImageView)findViewById(R.id.imageView3);
        ImageView iv=(ImageView)findViewById(R.id.imageView);
        iv.setVisibility(View.INVISIBLE);
        iv3.setVisibility(View.INVISIBLE);

        ImageView iv5=(ImageView)findViewById(R.id.imageView5);

        mediaPlayer1 = MediaPlayer.create(this, R.raw.love_you);
        mediaPlayer1.setLooping(true);
        play1();
        mediaPlayer2 = MediaPlayer.create(this, R.raw.marry_me);
        mediaPlayer2.setLooping(true);

        Button b=(Button)findViewById(R.id.button2);
        b.setTextSize(20);

        snowshow();
    }
    private MediaPlayer mediaPlayer1;//MediaPlayer对象
    private MediaPlayer mediaPlayer2;


    public void myClick(View view)
    {
        String str="1";
        play1();
    }

    private void play1() {
        if(mediaPlayer1.isPlaying()) {
            mediaPlayer1.pause();
        }
        else{
            mediaPlayer1.start();

        }
    }
    private void play2() {
        if(mediaPlayer2.isPlaying()) {
            mediaPlayer2.pause();
        }
        else{
            mediaPlayer2.start();

        }
    }
    public void showdialog(View view)
    {
        //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("检测到正在播放");
        //alertdialogbuilder.setPositiveButton("确定", click1);
        //alertdialogbuilder.setNegativeButton("取消", click2);
        AlertDialog alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    boolean a = false;
    public void button_click(View view)
    {
        if (a == false) {
            mediaPlayer1.stop();
            mediaPlayer1.release();
           // ImageView iv4=(ImageView)findViewById(R.id.imageView4);
            //iv4.setVisibility(View.INVISIBLE);
            lHandler.post(lRunnable);
            a = true;
            play2();
            Button bb =(Button)findViewById(R.id.button2);
            bb.setVisibility(View.INVISIBLE);
        }
    }

    private Handler lHandler = new Handler();
    private Runnable lRunnable = new Runnable() {
        public void run() {

            ImageView iv3=(ImageView)findViewById(R.id.imageView3);
            ImageView iv=(ImageView)findViewById(R.id.imageView);
            if ( iv3.getVisibility() == View.INVISIBLE  ) {
                iv3.setVisibility(View.VISIBLE);
                iv.setVisibility(View.INVISIBLE);
            }
            else{
                iv3.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.VISIBLE);
            }

            // 每3秒执行一次
            lHandler.postDelayed(lRunnable, 100);  //给自己发送消息，自运行
        }
    };

    private com.example.administrator.myapplication.FlowerView mFlowerView;
    // 屏幕宽度
    public static int screenWidth;
    // 屏幕高度
    public static int screenHeight;
    Timer myTimer = null;
    TimerTask mTask = null;
    private static final int SNOW_BLOCK = 1;
    private Handler mHandler = new Handler() {
        public void dispatchMessage(Message msg) {
            mFlowerView.inva();
        };
    };

    void  snowshow() {
        mFlowerView = (FlowerView) findViewById(R.id.flowerview);
        screenWidth = getWindow().getWindowManager().getDefaultDisplay()
                .getWidth();
        screenHeight = getWindow().getWindowManager().getDefaultDisplay()
                .getHeight();

        DisplayMetrics dis = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dis);
        float de = dis.density;
        mFlowerView.setWH(screenWidth, screenHeight, de);
        mFlowerView.loadFlower();
        mFlowerView.addRect();

        myTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = SNOW_BLOCK;
                mHandler.sendMessage(msg);
            }
        };
        myTimer.schedule(mTask, 1000, 10);

    }

    @Override
    protected void onDestroy() {
        //将线程销毁掉
        lHandler.removeCallbacks(lRunnable);
        super.onDestroy();
        mFlowerView.recly();

    }

}

