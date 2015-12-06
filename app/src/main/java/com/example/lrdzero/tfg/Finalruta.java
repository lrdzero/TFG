package com.example.lrdzero.tfg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Finalruta extends AppCompatActivity {
    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalruta);

        final Animation af = AnimationUtils.loadAnimation(this,R.anim.animfinal);
        tv = (TextView)findViewById(R.id.textitem);
        tv.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.item);
        final ImageView flecha = (ImageView) findViewById(R.id.siguiente);
        flecha.setVisibility(View.INVISIBLE);
        final ImageView boca = (ImageView) findViewById(R.id.bocaverde);
        final ArrayList<Integer> recomps= new ArrayList<>();

        recomps.add(R.drawable.pesas);
        recomps.add(R.drawable.zapatillas);


        //recomps.add(new Items());

        flecha.startAnimation(PasaTexto());

        final RelativeLayout avatar = (RelativeLayout)findViewById(R.id.Avatar);
        af.setFillEnabled(true);
        af.setFillAfter(true);

        Animation ocultar = AnimationUtils.loadAnimation(this,R.anim.abc_fade_out);
        ocultar.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ocultar.setDuration(2000);




        af.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                tv.clearAnimation();
                iv.clearAnimation();
                flecha.getAnimation().cancel();
                boca.clearAnimation();
                flecha.getAnimation().reset();

                if(recomps.size()>0){
                    iv.setImageResource(recomps.remove(0));
                    tv.startAnimation(Mostrar(flecha));
                    iv.startAnimation(Mostrar(null));
                }
                else {
                    tv.setVisibility(View.INVISIBLE);
                    iv.setVisibility(View.INVISIBLE);
                    avatar.startAnimation(af);
                }


            }
        });
        boca.startAnimation(habla());
        tv.startAnimation(Mostrar(flecha));
        tv.setVisibility(View.VISIBLE);

    }

    public AnimationSet habla(){

        Animation habla = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.03);
        habla.setDuration(500);
        habla.setRepeatCount(20);
        habla.setRepeatMode(Animation.REVERSE);
        habla.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Random rand = new Random();

                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int randomNum = rand.nextInt((300 - 50) + 1) + 50;
                animation.setDuration(randomNum);
            }
        });


        AnimationSet as = new AnimationSet(true);
        as.addAnimation(habla);
        return as;
    }

    public Animation PasaTexto(){
        Animation agrandaSig = new ScaleAnimation((float)0.25,(float)1.25,(float)0.25,(float)1.25,Animation.RELATIVE_TO_SELF,(float)0.50,Animation.RELATIVE_TO_SELF,(float)0.50);

        agrandaSig.setDuration(500);
        agrandaSig.setRepeatCount(Animation.INFINITE);
        agrandaSig.setRepeatMode(Animation.REVERSE);


        return agrandaSig;
    }

    public  AnimationSet GiroOjos(){

        Animation mirada = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.035);
        Animation mirada2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)-0.035);

        mirada.setDuration(1000);
        mirada2.setDuration(1000);
        mirada2.setStartOffset(3000);


        AnimationSet as = new AnimationSet(true);
        as.addAnimation(mirada);
        as.addAnimation(mirada2);


        return as;

    }

    public Animation Mostrar(final View v){

        final Animation mostrar= AnimationUtils.loadAnimation(this,R.anim.abc_fade_in);
        mostrar.setDuration(2000);
        mostrar.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(v!=null)
                    v.startAnimation(PasaTexto());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return mostrar;
    }
}
