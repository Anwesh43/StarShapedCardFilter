package com.anwesome.ui.starshapedfiltercard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 26/05/17.
 */

public class StarShapedCardView extends View{
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int time = 0,w,h,direction=0,color = Color.parseColor("#0097A7");
    private Bitmap bitmap;
    public StarShapedCardView(Context context,Bitmap bitmap,int color,int direction) {
        super(context);
        this.bitmap = bitmap;
        this.color = color;
        this.direction = direction;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            bitmap = Bitmap.createScaledBitmap(bitmap,4*w/5,4*h/5,true);
        }
        int r1 = 2*w/5,r2 = w/6;
        canvas.save();
        canvas.translate(w/2,h/2);
        Path path = new Path();
        for(int i=0;i<6;i++) {
            float deg1 = i*60,deg2 = deg1-30,deg3 = deg1+30;
            float x1 = (float)(r1*Math.cos(deg1*Math.PI/180)),y1 = (float)(r1*Math.sin(deg1*Math.PI/180));
            float x2 = (float)(r2*Math.cos(deg2*Math.PI/180)),y2 = (float)(r2*Math.sin(deg2*Math.PI/180));
            float x3 = (float)(r2*Math.cos(deg3*Math.PI/180)),y3 = (float)(r2*Math.sin(deg3*Math.PI/180));
            if(i == 0) {
                path.moveTo(x2,y2);
                path.lineTo(x1,y1);
                path.lineTo(x3,y3);
            }
            else {
                path.lineTo(x2,y2);
                path.lineTo(x1,y1);
                path.lineTo(x3,y3);
            }
        }
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap,-bitmap.getWidth()/2,-bitmap.getHeight()/2,paint);
        canvas.restore();
        time++;
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return true;
    }
    private class ColorFilterRect {
        private float hFilter = 0;
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.translate(w/2,h/2);
            canvas.rotate(90*direction);
            canvas.drawRect(new RectF(-w/2,-h/2,w/2,-h/2+hFilter),paint);
            canvas.restore();
        }
        public void update(float factor) {
            hFilter = h*factor;
        }
    }
}
