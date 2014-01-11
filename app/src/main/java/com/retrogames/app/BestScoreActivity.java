package com.retrogames.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Tomasz on 11.01.14.
 */
public class BestScoreActivity extends Activity {

    private int gameType = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_score);

        Typeface font = Typeface.createFromAsset(getAssets(), ChooseGameActivity.FONT);
        TextView textView = (TextView) findViewById(R.id.best_score_text_view);
        TextView textViewScore = (TextView) findViewById(R.id.best_score_text_view_score);
        textView.setTypeface(font);
        textViewScore.setTypeface(font);

        Bundle bundle = getIntent().getExtras();
        gameType = bundle.getInt(ChooseGameActivity.INDEX_GAME_STRING);
        if (gameType == ChooseGameActivity.INDEX_TETRIS) {
            textViewScore.setText(String.valueOf(ChooseGameActivity.BEST_SCORE_TETRIS));
        }
        else if (gameType == ChooseGameActivity.INDEX_RACE) {
            textViewScore.setText(String.valueOf(ChooseGameActivity.BEST_SCORE_RACE));
        }
        else if (gameType == ChooseGameActivity.INDEX_TANKS) {
            textViewScore.setText(String.valueOf(ChooseGameActivity.BEST_SCORE_TANKS));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChooseGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra(ChooseGameActivity.INDEX_GAME_STRING, gameType);
        startActivity(intent);
        finish();
    }
}
