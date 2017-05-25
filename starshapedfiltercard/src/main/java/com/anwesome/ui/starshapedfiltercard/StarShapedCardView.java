package com.anwesome.ui.starshapedfiltercard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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
    private ColorFilterRect colorFilterRect;
    private AnimationHandler animationHandler;
    public static final int DIRECTION_UP = 0,DIRECTION_RIGHT = 1,DIRECTION_DOWN = 2 ,DIRECTION_LEFT = 3;
    private OnSelectionChangeListener onSelectionChangeListener;
    public void setOnSelectionChangeListener(OnSelectionChangeListener onSelectionChangeListener) {
        this.onSelectionChangeListener = onSelectionChangeListener;
    }
    public StarShapedCardView(Context context,Bitmap bitmap,int color,int direction) {
        super(context);
        this.bitmap = bitmap;
        this.color = color;
        this.direction = direction;
    }
    public void update(float factor) {
        colorFilterRect.update(factor);
        postInvalidate();
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            bitmap = Bitmap.createScaledBitmap(bitmap,4*w/5,4*h/5,true);
            colorFilterRect = new ColorFilterRect();
            animationHandler = new AnimationHandler();
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
        colorFilterRect.draw(canvas);
        canvas.restore();
        time++;
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            animationHandler.start();
        }
        return true;
    }
    private class ColorFilterRect {
        private float hFilter = 0;
        public void draw(Canvas canvas) {
            paint.setColor(Color.argb(150,Color.red(color),Color.green(color),Color.blue(color)));
            canvas.save();
            canvas.rotate(90*direction);
            canvas.drawRect(new RectF(-w/2,-h/2,w/2,-h/2+hFilter),paint);
            canvas.restore();
        }
        public void update(float factor) {
            hFilter = h*factor;
        }
    }
    private class AnimationHandler extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private int dir = 0;
        private boolean isAnimating = false;
        private ValueAnimator startAnim = ValueAnimator.ofFloat(0,1),endAnim = ValueAnimator.ofFloat(1,0);
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float factor = (float)valueAnimator.getAnimatedValue();
            update(factor);
        }
        public void onAnimationEnd(Animator animator) {
            if(isAnimating) {
                if(onSelectionChangeListener != null) {
                    if (dir == 0) {
                        onSelectionChangeListener.onSelect();
                    } else {
                        onSelectionChangeListener.onUnSelect();
                    }
                }
                dir = dir == 0?1:0;
                isAnimating = false;
            }
        }
        public void start() {
            if(!isAnimating) {
                if(dir == 0) {
                    startAnim.start();
                }
                else {
                    endAnim.start();
                }
                isAnimating = true;
            }
        }
        public AnimationHandler() {
            startAnim.setDuration(500);
            endAnim.setDuration(500);
            startAnim.addUpdateListener(this);
            endAnim.addUpdateListener(this);
            startAnim.addListener(this);
            endAnim.addListener(this);
        }
    }
}
