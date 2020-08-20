package com.anand.android.onsitetask2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    int flag = 0;

    Rect rect=new Rect();

    int[] pointsX = new int[2];
    int[] pointsY = new int[2];
    int corner;
    Paint blue=new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        blue.setColor(Color.BLUE);
        int Xa=pointsX[0];
        int Xb=pointsX[1];
        if(Xa>Xb){
            pointsX[0]=Xb;
            pointsX[1]=Xa;
        }
        int Ya=pointsY[0];
        int Yb=pointsY[1];
        if (Ya>Yb){
           pointsY[0]=Yb;
           pointsY[1]=Ya;
        }
        if (flag == 0) {
            rect.set(getWidth()/2-250, getHeight()/2-250, getWidth()/2+250, getHeight()/2+250);
            canvas.drawRect(400, 600, 900, 1100, blue);
            pointsX[0] = getWidth()/2-250;
            pointsY[0] = getHeight()/2-250;
            pointsX[1] = getWidth()/2+250;
            pointsY[1] = getHeight()/2+250;

            flag = 1;
        } else {

            if (corner == 1 || corner == 2 || corner == 3 || corner == 4) {
                rect.set(pointsX[0], pointsY[0], pointsX[1], pointsY[1]);
                canvas.drawRect(rect, blue);
            } else if (corner == 5) {
                canvas.drawRect(rect, blue);
            } else if (corner == 6) {
                rect.set(pointsX[0], pointsY[0], pointsX[1], pointsY[1]);
                canvas.drawRect(rect, blue);
            } else {
                canvas.drawRect(getWidth()/2f-250, getHeight()/2f-250, getWidth()/2f+250, getHeight()/2f+250, blue);
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointX = (int) event.getX();
        int pointY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if ((pointX > pointsX[0] - 40 && pointX < pointsX[0] + 40) && (pointY > pointsY[0] - 40 && pointY < pointsY[0] + 40)) {
                    corner = 1; // Corner 1 touched
                } else if ((pointX > pointsX[1] - 40 && pointX < pointsX[1] + 40) && (pointY > pointsY[1] - 40 && pointY < pointsY[1] + 40)) {
                    corner = 2; // Corner 2 touched
                } else if ((pointX > pointsX[1] - 40 && pointX < pointsX[1] + 40) && (pointY > pointsY[0] - 40 && pointY < pointsY[0] + 40)) {
                    corner = 3; // Corner 3 touched
                } else if ((pointX > pointsX[0] - 40 && pointX < pointsX[0] + 40) && (pointY > pointsY[1] - 40 && pointY < pointsY[1] + 40)) {
                    corner = 4; // Corner 4 touched
                } else if ((pointX > pointsX[0] + 50 && pointX < pointsX[1] - 50) && (pointY > pointsY[0] + 50 && pointY < pointsY[1] - 50)) {
                    corner = 5; //Rectangle touched
                }
                break;
                
            case MotionEvent.ACTION_MOVE:
                //Rectangle Resize
                if (corner == 1) {
                    pointsX[0] = pointX;
                    pointsY[0] = pointY;

                } else if (corner == 2) {
                    pointsX[1] = pointX;
                    pointsY[1] = pointY;

                } else if (corner == 3) {
                    pointsX[1] = pointX;
                    pointsY[0] = pointY;

                } else if (corner == 4) {
                    pointsX[0] = pointX;
                    pointsY[1] = pointY;
                } else if (corner == 5) {
                    //Rectangle Moves
                    int dX = rect.width();
                    int dY = rect.height();

                    if (pointX > dX / 2 && pointY > dY / 2 && (pointX + dX / 2) < 1085 && (pointY + dY / 2) < 2015) {
                        rect.left = pointX - dX / 2;
                        rect.top = pointY - dY / 2;
                        rect.right = rect.left + dX;
                        rect.bottom = rect.top + dY;

                        pointsX[0] = rect.left;
                        pointsX[1] = rect.right;
                        pointsY[0] = rect.top;
                        pointsY[1] = rect.bottom;

                    } else if (pointX < dX / 2) {
                        if (pointY > dY / 2 && (pointY + dY / 2) < 2015) {
                            rect.top = pointY - dY / 2;
                            rect.bottom = rect.top + dY;

                            pointsX[0] = rect.left;
                            pointsX[1] = rect.right;
                            pointsY[0] = rect.top;
                            pointsY[1] = rect.bottom;
                        }

                    } else if ((pointX + dX / 2) > 1085) {
                        if (pointY > dY / 2 && (pointY + dY / 2) < 2015) {
                            rect.top = pointY - dY / 2;
                            rect.bottom = rect.top + dY;

                            pointsX[0] = rect.left;
                            pointsX[1] = rect.right;
                            pointsY[0] = rect.top;
                            pointsY[1] = rect.bottom;
                        }
                    } else if (pointY < dY / 2) {
                        if (pointX > dX / 2 && (pointX + dX / 2) < 1085) {
                            rect.left = pointX - dX / 2;
                            rect.right = rect.left + dX;
                            pointsX[0] = rect.left;
                            pointsX[1] = rect.right;
                            pointsY[0] = rect.top;
                            pointsY[1] = rect.bottom;

                        }
                    } else if ((pointY + dY / 2) > 2015) {
                        rect.left = pointX - dX / 2;
                        rect.right = rect.left + dX;
                        pointsX[0] = rect.left;
                        pointsX[1] = rect.right;
                        pointsY[0] = rect.top;
                        pointsY[1] = rect.bottom;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                corner = 6;
                break;

            default:
                return false;
        }
        postInvalidate();
        return true;
    }
}