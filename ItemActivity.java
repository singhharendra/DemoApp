package com.example.harendra.mydemoapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemActivity extends Activity {
    Button buttonColoone,buttonColotwo,buttonColothree,buttonMove;
    TextView textView,textViewSelected;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        textViewSelected=(TextView)findViewById(R.id.seltedtext);
        relativeLayout=(RelativeLayout)findViewById(R.id.relcolview);

    }
    public void onClick(View view) {
        Fragment fr;
        switch (view.getId()) {
            case R.id.buttonfragtwo:
                fr = new FragmentTwo();
                textViewSelected.setText("ButtonTwo");
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fm2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_place, fr);
                fragmentTransaction2.commit();
                break;
            case R.id.buttonfragthree:
                fr = new FragmentThree();
                textViewSelected.setText("ButtonThree");
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fm3.beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_place, fr);
                fragmentTransaction3.commit();
                break;
            case R.id.buttonfragfour:
                fr = new FragmentFour();
                textViewSelected.setText("ButtonFour");
                FragmentManager fm4 = getFragmentManager();
                FragmentTransaction fragmentTransaction4 = fm4.beginTransaction();
                fragmentTransaction4.replace(R.id.fragment_place, fr);
                fragmentTransaction4.commit();
                break;
            case R.id.buttonfragfive:
                fr = new FragmentFive();
                textViewSelected.setText("ButtonFive");
                FragmentManager fm5 = getFragmentManager();
                FragmentTransaction fragmentTransaction5 = fm5.beginTransaction();
                fragmentTransaction5.replace(R.id.fragment_place, fr);
                fragmentTransaction5.commit();
                break;
            case R.id.buttonfragone:
                fr = new FragmentOne();
                textViewSelected.setText("ButtoOne");
                FragmentManager fm1 = getFragmentManager();
                FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
                fragmentTransaction1.replace(R.id.fragment_place, fr);
                fragmentTransaction1.commit();
                break;
            case R.id.buttoncolorone:
                relativeLayout.setBackgroundColor(Color.BLUE);
                break;
            case R.id.buttoncolortwo:
                relativeLayout.setBackgroundColor(Color.RED);
                break;
            case R.id.buttoncolorthree:
                relativeLayout.setBackgroundColor(Color.YELLOW);
                break;

            case R.id.buttonmove:
                startActivityForResult(new Intent(ItemActivity.this,TransportActivity.class),1);
                break;
            default:
                 break;

        }
    }



}
