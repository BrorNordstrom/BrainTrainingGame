package com.example.andreasnordstrom.madcw1.activity;

/**
 * Created by  Bror Nordstrom on 2018-02-28.
 * game managing model
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.example.andreasnordstrom.madcw1.R;
import com.example.andreasnordstrom.madcw1.model.GameModel;
import com.example.andreasnordstrom.madcw1.util.Global;

    import java.util.Random;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        rand = new Random();

        // define button click event
        findViewById(R.id.button_novice).setOnClickListener(this);
        findViewById(R.id.button_easy).setOnClickListener(this);
        findViewById(R.id.button_medium).setOnClickListener(this);
        findViewById(R.id.button_guru).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        // when click one of 4 button, select game level as button
        switch (id) {
            case R.id.button_novice:
                Global.difficulty = 1;
                break;
            case R.id.button_easy:
                Global.difficulty = 2;
                break;
            case R.id.button_medium:
                Global.difficulty = 3;
                break;
            case R.id.button_guru:
                Global.difficulty = 4;
                break;
        }

        // and start game
        doStartGame();

    }

    /**
     * after set initial option start new game.
     * and generate all questions in random.
     */
    private void doStartGame() {

        // get hint status for check box
        Global.isHint = ((CheckBox) findViewById(R.id.check_hint)).isChecked();

        // and generate expression as random
        for (int i = 0; i < Global.TOTAL_COUNT; i++) {

            // get integer number as game difficulty
            // for ex : when select novice, integer count is 2
            // this is requirement
            int count = doGetCountNumber(Global.difficulty);

            int point = Integer.MIN_VALUE, number = 0;
            String expression = "", op = "";

            // generate expression and calculate that value
            for (int j = 0; j < count; j++) {

                // generate integer and operation for making expression.
                int number1 = rand.nextInt(10) + 1;
                String op1 = Global.operators[rand.nextInt(4)];

                // and calculate correct answer as requirement.
                if (number != 0 && point == Integer.MIN_VALUE) point = calculator(number, number1, op);
                else if (number != 0) point = calculator(point, number1, op);

                expression = String.format("%s%s%s", expression,
                        String.valueOf(number1), op1);

                number = number1;
                op = op1;
            }

            // get full expression and save it in global variable
            expression = expression.substring(0, expression.length() - 1);
            Global.gameModels[i] = new GameModel(expression, point,
                    false, 0, 0, 0);

        }

        // go to calcActivity
        startActivity(new Intent(this, CalcActivity.class));
        finish();

    }

    /**
     * get number count as game difficulty
     * @param mode : game difficulty
     * @return : count
     */
    private int doGetCountNumber(int mode) {

        int count = 0;

        switch (mode) {
            case 1:
                count = 2;
                break;
            case 2:
                count = rand.nextInt(2) + 2;
                break;
            case 3:
                count = rand.nextInt(3) + 2;
                break;
            case 4:
                count = rand.nextInt(3) + 4;
                break;
        }

        return count;
    }

    private int calculator(int m, int n, String op) {

        int ret = 0;

        switch (op) {
            case "+":
                ret = m + n;
                break;
            case "-":
                ret = m - n;
                break;
            case "*":
                ret = m * n;
                break;
            case "/":
                ret = Math.round((float) m / (float) n);
                break;
        }

        return ret;
    }

}
