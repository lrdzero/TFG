package com.example.lrdzero.animations;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.lrdzero.activities.R;

import java.util.Random;

/**
 * Created by javi_ on 11/12/2015.
 */
public class Animations {

    public Animations(){}
    /**
     *
     * Animacion del saludo del avatar
     *
     *
     */
    public Animation saludo(Context c){


        return AnimationUtils.loadAnimation(c, R.anim.animacionbrazoizq);
    }

    /**
     *
     * Animacion de movimiento de ojos del avatar
     *
     *
     */
    public AnimationSet GiroOjos(){

        Animation mirada = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.035,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        Animation mirada2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)-0.035,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);

        mirada.setDuration(1800);
        mirada2.setDuration(1800);
        mirada2.setStartOffset(3000);


        AnimationSet as = new AnimationSet(true);
        as.addAnimation(mirada);
        as.addAnimation(mirada2);


        return as;

    }


    /**
     *
     * Animacion del avatar hablando
     *
     *
     */
    public AnimationSet habla(){

        Animation habla = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,(float)0.03);
        habla.setDuration(500);
        habla.setRepeatCount(Animation.INFINITE);
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


}
