package com.anwesome.ui.starshapedfiltercard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 26/05/17.
 */

public class ListLayout  extends ViewGroup{
    private int w,h,viewSize;
    public ListLayout(Context context) {
        super(context);
        initDimension(context);
    }
    public void initDimension(Context context) {
        DisplayManager displayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display != null) {
            Point size = new Point();
            w = size.x;
            h = size.y;
            viewSize = w/2;
        }
    }
    public void addCard(Bitmap bitmap,int color,int direction) {
        StarShapedCardView starShapedCardView = new StarShapedCardView(getContext(),bitmap,color,direction);
        addView(starShapedCardView,new LayoutParams(viewSize,viewSize));
        requestLayout();
    }
    public void onMeasure(int wspec,int hspec) {
        int hMax = h/25;
        for(int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            measureChild(child,wspec,hspec);
            hMax+=(child.getMeasuredHeight()+h/25);
        }
        setMeasuredDimension(w,Math.max(h,hMax+h/25));
    }
    public void onLayout(boolean reloaded,int a,int b,int wa,int ha) {
        int x = w/2-viewSize/2,y = h/25;
        for(int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            child.layout(x,y,x+viewSize,y+viewSize);
            y+=(viewSize+h/25);
        }
    }
}
