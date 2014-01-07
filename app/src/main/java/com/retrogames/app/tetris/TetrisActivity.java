package com.retrogames.app.tetris;

import android.app.Activity;
import android.content.Context;;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisActivity extends Activity {

    TetrisSurfaceView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new TetrisSurfaceView(this);
        setContentView(view);
    }

    public void closeActivity() {
        super.onBackPressed();
    }
}
