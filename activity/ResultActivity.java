package com.example.andreasnordstrom.madcw1.activity;

/**
 * Created by  Bror Nordstrom on 2018-02-28.
 * game managing model
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.andreasnordstrom.madcw1.R;
import com.example.andreasnordstrom.madcw1.util.Global;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Global.current_number = 0;

        // display game result
        int total_score = 0;

        // calculate total score of game
        for (int i = 0; i < Global.TOTAL_COUNT; i++) {
            total_score += Global.gameModels[i].getPoint();
        }

        // and show result in total score text view
        ((TextView) findViewById(R.id.text_total_score)).setText(String.valueOf(total_score));

    }

    // when click "Go To Main" button, go to main page
    public void gotoMain(View view) {
        finish();
    }
}
