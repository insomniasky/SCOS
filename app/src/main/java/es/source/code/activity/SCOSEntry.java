package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SCOSEntry extends Activity implements OnTouchListener,
        OnGestureListener {
    GestureDetector mGestureDetector;
    private static final int FLING_MIN_DISTANCE = 50;
    private static final int FLING_MIN_VELOCITY = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        mGestureDetector = new GestureDetector(this, this);
        RelativeLayout sLayout = (RelativeLayout) findViewById(R.id.scosentry);
        sLayout.setOnTouchListener(this);
        sLayout.setLongClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("touch", "touch");
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {

            Intent intent = new Intent();
            intent.putExtra("from", "FromEntry");
            intent.setClass(SCOSEntry.this, MainScreen.class);
            startActivity(intent);
            Toast.makeText(this, "系统主页面", Toast.LENGTH_SHORT).show();
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {

            Intent intent = new Intent(SCOSEntry.this, MainScreen.class);
            startActivity(intent);
            Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}