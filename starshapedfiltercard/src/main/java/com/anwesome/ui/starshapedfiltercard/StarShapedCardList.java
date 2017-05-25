package com.anwesome.ui.starshapedfiltercard;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 26/05/17.
 */

public class StarShapedCardList {
    private Activity activity;
    private ScrollView scrollView;
    private ListLayout listLayout;
    private boolean isShown = false;
    public StarShapedCardList(Activity activity) {
        this.activity = activity;
        scrollView = new ScrollView(activity);
        listLayout = new ListLayout(activity);
        scrollView.addView(listLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void addCard(Bitmap bitmap,int color,int dir) {
        if(!isShown) {
            listLayout.addCard(bitmap, color, dir);
        }
    }
    public void show() {
        if(!isShown) {
            activity.setContentView(scrollView);
            isShown = true;
        }
    }
}
