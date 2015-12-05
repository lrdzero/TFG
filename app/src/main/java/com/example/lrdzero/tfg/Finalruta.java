package com.example.lrdzero.tfg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class Finalruta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalruta);

        Animation af = AnimationUtils.loadAnimation(this,R.anim.animfinal);

        RelativeLayout avatar = (RelativeLayout)findViewById(R.id.Avatar);
        af.setFillEnabled(true);
        af.setFillAfter(true);
        avatar.startAnimation(af);

    }
}
