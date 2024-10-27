package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.id_logo);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f,1f,0f,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1f,0f,1f,0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation2.setFillAfter(true);
        scaleAnimation.setDuration(2000);
        scaleAnimation2.setDuration(1000);
        logo.startAnimation(scaleAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo.startAnimation(scaleAnimation2);
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent launchGame = new Intent(getApplicationContext(),LoginHost.class);
                startActivity(launchGame);
                finish();
            }
        },2000);
    }
}