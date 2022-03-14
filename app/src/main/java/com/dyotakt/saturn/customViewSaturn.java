package com.dyotakt.saturn;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class customViewSaturn extends SurfaceView implements SurfaceHolder.Callback2{

    private final SurfaceHolder mHolder;

    private int mViewWidth;
    private int mViewHeight;
    private SaturnRings saturnRings;
    Context cont;

    private int degree = 27;
    private int ringSize = 52;
    private boolean goingUp = true;
    private int outerRingWidth, innerRingWidth;
    private boolean goingDown=true;
    public customViewSaturn(Context context) {
        super(context);
        cont = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
        drawViewOnSystemCall(holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mViewWidth = getWidth();
        mViewHeight = getHeight();

        saturnRings = new SaturnRings(mViewWidth, mViewHeight, 27);

        drawViewOnSystemCall(holder);

        AnimationThread galleryThread = new AnimationThread(getHolder());
        galleryThread.setRunning(true);
        galleryThread.start();
    }

    private void drawViewOnSystemCall(SurfaceHolder holder) {
        synchronized (holder) {
            refreshScreen();
        }
    }

    private void refreshScreen() {
        Canvas lockCanvas = mHolder.lockCanvas();
        drawSurfaceView(lockCanvas);
        mHolder.unlockCanvasAndPost(lockCanvas);
    }

    public void updatePosition() {

        if(degree == 27) {
            goingUp = false;
            outerRingWidth = 0;
            innerRingWidth = 0;
        }
        if(degree == -27) {
            goingUp = true;
            outerRingWidth = 0;
            innerRingWidth = 0;
        }

        if(goingUp) {
            degree += 1;
            if(goingDown) {
                if(outerRingWidth>ringSize) goingDown = false;
                outerRingWidth+=2;
                innerRingWidth+=1;
            }
            if(!goingDown) {
                if(outerRingWidth<-ringSize) goingDown = true;
                outerRingWidth-=2;
                innerRingWidth-=1;
            }
        }

        if(!goingUp) {
            degree -= 1;
            if(goingDown) {
                if(outerRingWidth>ringSize) goingDown = false;
                outerRingWidth+=2;
                innerRingWidth+=1;
            } if(!goingDown) {
                if(outerRingWidth<-ringSize) goingDown = true;
                outerRingWidth-=2;
                innerRingWidth-=1;
            }
        }

        saturnRings.newPosition(degree, outerRingWidth, innerRingWidth, goingUp);
        invalidate();
        drawViewOnSystemCall(mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawViewOnSystemCall(holder);
    }

    private void drawSurfaceView(Canvas canvas) {
        if (canvas == null) {
            return;
        } else {
            canvas.drawRGB(0,0,0);
        }
        if (saturnRings != null) {
            saturnRings.render(canvas);
        }
        invalidate();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class AnimationThread extends Thread {
        private boolean isRunning;
        private final SurfaceHolder mHolder;

        public AnimationThread(SurfaceHolder holder) {
            mHolder = holder;
        }

        public void setRunning(boolean running) {
            isRunning = running;
        }

        @Override
        public void run() {
            while (isRunning) {
                updatePosition();
                invalidate();
                drawViewOnSystemCall(mHolder);
            }
        }
    }
}

