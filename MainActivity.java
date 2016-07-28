package com.example.mi.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
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
    private Button mBbtnStart,mBtnCancel;
    private  float widthTV,heightTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtv = (TextView) findViewById(R.id.tv);


        mBbtnStart=(Button) findViewById(R.id.btnStart);
        mBtnCancel=(Button) findViewById(R.id.btnCancle);

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

    }

    private void CancleAnimation() {
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

        float scale=po.x/widthTV;
        Log.e("StartAnimation()","scale="+scale+",heightTV="+heightTV+
                ",widthTV="+widthTV+",po.x="+po.x );


        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playTogether(
                ObjectAnimator.ofFloat(mtv, View.TRANSLATION_X, offset[0]),
                ObjectAnimator.ofFloat(mtv, View.TRANSLATION_Y, offset[1]),
                ObjectAnimator.ofFloat(mtv, View.SCALE_X, scale),
                ObjectAnimator.ofFloat(mtv, View.SCALE_Y, scale)
        );

        set.start();
    }

}
