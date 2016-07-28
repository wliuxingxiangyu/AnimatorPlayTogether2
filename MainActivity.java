package com.example.mi.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mtv;
    private Button mBbtnStart,mBtnCancel,mbtnB;
    private  float widthTV,heightTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtv = (TextView) findViewById(R.id.tv);


        mBbtnStart=(Button) findViewById(R.id.btnStart);
        mBtnCancel=(Button) findViewById(R.id.btnCancle);
        mbtnB=(Button) findViewById(R.id.btnB);
        mBbtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartAnimation();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancleAnimation();
            }
        });

        mbtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Bactivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


    }

    private void CancleAnimation() {
        Log.e("CancleAnimation()","------------");
        float scale=mtv.getWidth()/widthTV;//mtv.getWidth()在动画过程中一直未变，原来的宽度。
        Log.e("CancleAnimation()","scale="+scale+
                ",mtv.getWidth()="+mtv.getWidth()+",widthTV="+widthTV);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playTogether(
                ObjectAnimator.ofFloat(mtv, View.SCALE_X, scale),
                ObjectAnimator.ofFloat(mtv, View.SCALE_Y, scale)
        );

        set.start();
    }

    public void StartAnimation( ){
        Log.e("StartAnimation()","------------");
        Display display = ((WindowManager)getSystemService(Service.WINDOW_SERVICE)).getDefaultDisplay();
        Point po = new Point();
        display.getRealSize(po);

        int[] leftTopCoor = new int[2];
        int[] centerCoor = new int[2];
        mtv.getLocationOnScreen(leftTopCoor);
        widthTV=mtv.getWidth();
        heightTV=mtv.getHeight();
        centerCoor[0]=(int)widthTV/2+leftTopCoor[0];
        centerCoor[1]=(int)heightTV/2+leftTopCoor[1];
        float[] offset=new float[2];
        offset[0]=po.x/2-centerCoor[0];
        offset[1]=po.y/2-centerCoor[1];

        float scaleX=po.x/widthTV;
        float scaleY=po.y/heightTV;
        Log.e("StartAnimation()","scaleX="+scaleX+",scaleY="+scaleY+
                ",heightTV="+heightTV+",po.y="+po.y+
                ",widthTV="+widthTV+",po.x="+po.x );


        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playTogether(
                ObjectAnimator.ofFloat(mtv, View.TRANSLATION_X, offset[0]),
                ObjectAnimator.ofFloat(mtv, View.TRANSLATION_Y, offset[1]),
                ObjectAnimator.ofFloat(mtv, View.SCALE_X, scaleX),
                ObjectAnimator.ofFloat(mtv, View.SCALE_Y, scaleX)
        );

        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int[] leftTopCoor = new int[2];
                mtv.getLocationOnScreen(leftTopCoor);
                Log.e("onAnimationEnd()--","left="+leftTopCoor[0]+",top="+leftTopCoor[1]);
            }
        });
    }

}
