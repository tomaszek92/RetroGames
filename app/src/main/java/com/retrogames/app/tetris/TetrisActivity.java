package com.retrogames.app.tetris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TetrisView(this));
    }

    public class TetrisView extends View implements View.OnTouchListener {

        Context _context;

        public TetrisView(Context context) {
            super(context);
            _context = context;
        }

        public boolean onTouch(View view, MotionEvent event) {
            String string = "X: " + event.getX() + " Y: " + event.getY();
            Toast.makeText(_context, string, Toast.LENGTH_LONG).show();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {;
            canvas.drawRGB(255,0,0);
        }
    }
}
