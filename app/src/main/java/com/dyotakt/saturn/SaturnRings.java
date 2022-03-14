package com.dyotakt.saturn;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class SaturnRings {

    private float rotate_angle, heightCenter, widthCenter;
    private int outerRingWidth = 54, innerRingWidth = 27;
    private Paint saturnPaint, ringsPaint;
    private boolean semiOnBot;


    public SaturnRings(float width, float height, float tiltAngle) {
        heightCenter = width/2;
        widthCenter = height/2;
        rotate_angle = tiltAngle; //actual tilt is 26.7 degrees
        createVisiblePaint();
        newPosition(0,54,27, false);
    }


    public void createVisiblePaint() {
        saturnPaint = new Paint();
        saturnPaint.setAntiAlias(true);
        saturnPaint.setFilterBitmap(true);
        saturnPaint.setDither(true);
        saturnPaint.setStyle(Paint.Style.FILL);
        saturnPaint.setColor(Color.rgb(195,146,79));

        ringsPaint = new Paint();
        ringsPaint.setAntiAlias(true);
        ringsPaint.setFilterBitmap(true);
        ringsPaint.setDither(true);
        ringsPaint.setStyle(Paint.Style.FILL);
        ringsPaint.setColor(Color.rgb(237,219,173));
    }

    public void render(Canvas canvas) {

        canvas.rotate(-rotate_angle, heightCenter, widthCenter);

        RectF outerRing = new RectF(
                heightCenter-200,
                widthCenter-outerRingWidth,
                heightCenter+200,
                widthCenter+outerRingWidth
        );
        RectF innerRing = new RectF(
                heightCenter-100,
                widthCenter-innerRingWidth,
                heightCenter+100,
                widthCenter+innerRingWidth
        );

        canvas.drawCircle(heightCenter, widthCenter, 100, ringsPaint);
        canvas.drawOval(outerRing, saturnPaint);
        canvas.drawOval(innerRing, ringsPaint);

        final RectF oval = new RectF();
        oval.set(heightCenter - 100,
                widthCenter - 100,
                heightCenter + 100,
                widthCenter + 100);

        canvas.drawArc(oval, semiOnBot?180:0, 180, false, ringsPaint);
        canvas.rotate(rotate_angle, heightCenter, widthCenter);
    }

    void newPosition(int degree, int orw, int irw, boolean semiOnBottom) {
        rotate_angle = degree;
        outerRingWidth = orw;
        innerRingWidth = irw;
        semiOnBot = semiOnBottom;
    }
}
