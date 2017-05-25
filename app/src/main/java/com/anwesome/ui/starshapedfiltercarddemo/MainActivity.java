package com.anwesome.ui.starshapedfiltercarddemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.starshapedfiltercard.StarShapedCardList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.stp);
        int colors[] = {Color.parseColor("#00838F"),Color.parseColor("#f44336"),Color.parseColor("#F57F17"),Color.parseColor("#D81B60"),Color.parseColor("#1E88E5")};
        StarShapedCardList starShapedCardList = new StarShapedCardList(this);
        for(int i=0;i<colors.length;i++) {
            starShapedCardList.addCard(bitmap, colors[i],i%4);
        }
        starShapedCardList.show();
    }
}
