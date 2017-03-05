package com.banksy.expandablecalendar;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private ViewFlipper mViewFlipper;
  private GestureDetectorCompat gestureDetector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mViewFlipper = (ViewFlipper) findViewById(R.id.mViewFlipper);
    gestureDetector = new GestureDetectorCompat(this, onGestureListener);
    mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
      }
    });
  }


  GestureDetector.OnGestureListener onGestureListener = new MyOnGestureListener(){
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      Log.d(TAG, "onFling: Fling in >>>>>>");
      if (e1.getX() - e2.getX() > 80) {
        Log.d(TAG, "onFling: >>>>>> fling ring");
        return true;
      }else if (e1.getX() - e2.getX() < -80){
        Log.d(TAG, "onFling: >>>>>> fling Left");
        return true;
      }
      return false;
    }
  };


  static class MyOnGestureListener implements  GestureDetector.OnGestureListener{

    @Override public boolean onDown(MotionEvent e) {
      return false;
    }

    @Override public void onShowPress(MotionEvent e) {

    }

    @Override public boolean onSingleTapUp(MotionEvent e) {
      return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      return false;
    }

    @Override public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      return false;
    }
  }
}
