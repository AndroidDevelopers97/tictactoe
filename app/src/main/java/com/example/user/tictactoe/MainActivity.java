package com.example.user.tictactoe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ImageView> imageButtons;
    private int ids[] = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    private boolean player;
    private final String LAMP= "Lamp";
    private final String BOMB = "Bomb";
    private int cases[][]={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}},turnNo=0;
    private TextView pText;
    private ImageView pImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageButtons = new ArrayList<>();
        player = true;
        for (int i = 0; i < ids.length; i++) {
            imageButtons.add((ImageView) findViewById(ids[i]));
            imageButtons.get(i).setOnClickListener(this);
            imageButtons.get(i).setContentDescription("1"+i);
        }

        pText=findViewById(R.id.ptext);
        pImage=findViewById(R.id.pimage);

    }

    @Override
    public void onClick(View v) {
        turnNo++;
        for (int i = 0; i < ids.length; i++) {

            if (imageButtons.get(i) == v) {
                onTurn(i);
            }
        }

    }

    private void onTurn(int index) {
        ImageView tempIB = imageButtons.get(index);

        tempIB.setClickable(false);
        if (player){                    //LAMP
            pText.setText(BOMB);
            pImage.setImageResource(R.drawable.bomb_icon);
            tempIB.setImageResource(R.drawable.lamp);
            tempIB.setContentDescription("0");
            player=false;
        }
        else{                           //bomb
            pText.setText(LAMP);
            pImage.setImageResource(R.drawable.lamp_icon);
            tempIB.setImageResource(R.drawable.bomb);
            tempIB.setContentDescription("1");
            player=true;
        }
        if(turnNo>4)
            checkCases();
    }

    private void checkCases() {
        boolean flag=true;
        for (int[] aCase : cases) {
            if (imageButtons.get(aCase[0]).getContentDescription().equals(imageButtons.get(aCase[1]).getContentDescription()) &&
                    imageButtons.get(aCase[1]).getContentDescription().equals(imageButtons.get(aCase[2]).getContentDescription())) {
                alertDialog(Integer.parseInt(imageButtons.get(aCase[2]).getContentDescription().toString()));
                flag = false;
                break;
            }
        }
        if(turnNo==9 && flag){
            int TIED = 2;
            alertDialog(TIED);
        }
    }

    @SuppressLint("SetTextI18n")
    private void alertDialog(int whichPlayer) {
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_alert);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView winOrTie=dialog.findViewById(R.id.winOrTie);
        TextView player = dialog.findViewById(R.id.playerName);
        ImageView image = dialog.findViewById(R.id.pImage);
        final ImageView star1=dialog.findViewById(R.id.star1);
        final ImageView star2=dialog.findViewById(R.id.star2);
        final ImageView star3=dialog.findViewById(R.id.star3);

        if(whichPlayer == 0){
            player.setText(LAMP);
            image.setImageResource(R.drawable.lamp_icon);
        }
        else if(whichPlayer == 1){
            player.setText(BOMB);
            image.setImageResource(R.drawable.bomb_icon);
        }
        else{
            image.setImageResource(R.drawable.tied);
            String TIED = "Tied";
            winOrTie.setText(TIED);
            player.setText("Try Again");
        }

        dialog.findViewById(R.id.restartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i=getIntent();
                finish();
                startActivity(i);
            }
        });
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                star1.setVisibility(View.VISIBLE);
                star1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_from_up));
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                star2.setVisibility(View.VISIBLE);
                star2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_from_up));
            }
        },4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                star3.setVisibility(View.VISIBLE);
                star3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_from_up));
            }
        },6000);
    }

    public void restart(View v) {
        Intent i=getIntent();
        finish();
        startActivity(i);
    }
}
