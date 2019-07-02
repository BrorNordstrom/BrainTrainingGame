package com.example.andreasnordstrom.madcw1.activity;

/**
 * Created by  Bror Nordstrom on 2018-02-28.
 * game managing model
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.andreasnordstrom.madcw1.R;
import com.example.andreasnordstrom.madcw1.util.Global;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class CalcActivity extends AppCompatActivity {

    // this is hint count
    private int hint_count = 4;

    // this is remaining time of round
    // initial value is 10s
    // that is Global.TOTAL_TIME
    private int remaining_time = Global.TOTAL_TIME;

    // context of activity
    private Context context;

    // answer text view
    private TextView text_answer;

    // timer and timeTask for counting.

    private Timer timer; // timer
    private TimerTask timerTask; // timerTask

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        context = this;
        text_answer = findViewById(R.id.text_answer);

        // show expression in TextView
        ((TextView) findViewById(R.id.text_expression)).setText(Global.gameModels[Global.current_number].getExpression());

        // show current number
        ((TextView) findViewById(R.id.text_number)).setText(String.format("%s.", String.valueOf(Global.current_number + 1)));

        // start time counter
        startTimer();

    }

    /**
     * when destroy activity, stop timer.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimerTask();
    }

    /**
     * input value for answer
     * when click number button, this event generate.
     */
    public void inputValue(View view) {

        int id = view.getId();
        String str = "";

        if (text_answer.getText().toString().contains("?")) text_answer.setText("");
// when entered, numbers be added in answer text view
        switch (id) {
            case R.id.button_num0:
                str = "0";
                break;
            case R.id.button_num1:
                str = "1";
                break;
            case R.id.button_num2:
                str = "2";
                break;
            case R.id.button_num3:
                str = "3";
                break;
            case R.id.button_num4:
                str = "4";
                break;
            case R.id.button_num5:
                str = "5";
                break;
            case R.id.button_num6:
                str = "6";
                break;
            case R.id.button_num7:
                str = "7";
                break;
            case R.id.button_num8:
                str = "8";
                break;
            case R.id.button_num9:
                str = "9";
                break;
            case R.id.button_minus:
                if (text_answer.getText().toString().length() == 0) str = "-";
                break;
        }

        text_answer.setText(String.format("%s%s", text_answer.getText().toString(), str));

    }

    /**
     * delete value of answer label
     */
    public void deleteValue(View view) {

        String str = text_answer.getText().toString();
// Delete text, when click "?" goes away and stays blank
        if (str.contains("?")) text_answer.setText("");
        if (str.length() == 0) return;

        str = str.substring(0, str.length() - 1);

        //Removes one character
        //Removes last integer
        text_answer.setText(str);

    }

    /**
     * calculate expression
     */
    public void calcExpression(View view) {
// When clicking #, calls this function
        // this is define evaluation text view
        TextView tv = findViewById(R.id.text_evaluation);
        // gone hint text view.
        // if isHint is true and incorrect result, it will show.
        findViewById(R.id.text_hint).setVisibility(View.GONE);

        // if text length of  evaluation text view is not 0,will go to next round.
        if (tv.getText().toString().length() != 0) {

            // next question
            // so increase current number of global class
            Global.current_number++;

            // if it greater than 10, go to result page, otherwise just go to next round
            if (Global.current_number < 10)  startActivity(new Intent(context, CalcActivity.class));
            else startActivity(new Intent(context, ResultActivity.class));

            finish();
            return;
        }

        int value; // this is result value that entered user
        try {
            // get value from text view
            // if invalid number format, exit this function.
            value = Integer.parseInt(text_answer.getText().toString());
        } catch (Exception e) {
            // this is exception part
            e.printStackTrace();
            return;
        }

        if (Global.isHint && hint_count > 0) {

            // when ishint is true and hint count greater that 0
            // in at time user can see hint. "greater" or "less"

            if (value == Global.gameModels[Global.current_number].getCorrect_answer()) {
                // when correct answer, show text "CORRECT" in evaluation text view.
                stopTimerTask();
                //Stops timer, for go to next round

                // answer is correct
                tv.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                tv.setText(R.string.correct);

                // this is score of answer
                // calculate score as requirement
                int point;
                if (remaining_time == 10) point = 100;
                else point = Math.round((float) 100 / (float) (Global.TOTAL_TIME - remaining_time));

                // this is part, game data save global variable.
                // score and time, status of answer and correcting.
                Global.gameModels[Global.current_number].setPoint(point);
                Global.gameModels[Global.current_number].setTime(Global.TOTAL_TIME - remaining_time);
                Global.gameModels[Global.current_number].setAnswer(true);
                Global.gameModels[Global.current_number].setUser_answer(value);

                // hint, greater or less than

            } else if (value > Global.gameModels[Global.current_number].getCorrect_answer()) {
                // less
                findViewById(R.id.text_hint).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.text_hint)).setText(R.string.less);
            } else {
                // greater
                findViewById(R.id.text_hint).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.text_hint)).setText(R.string.greater);
            }

            hint_count--;

        } else {

            // don't select hint option or hint count equals 0

            stopTimerTask();

            if (value == Global.gameModels[Global.current_number].getCorrect_answer()) {

                // answer is correct
                tv.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                tv.setText(R.string.correct);

                int point;
                if (remaining_time == 10) point = 100;
                else point = Math.round((float) 100 / (float) (Global.TOTAL_TIME - remaining_time));

                Global.gameModels[Global.current_number].setPoint(point);
                Global.gameModels[Global.current_number].setTime(Global.TOTAL_TIME - remaining_time);

            } else {

                // answer is wrong
                tv.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                tv.setText(R.string.wrong);
                Global.gameModels[Global.current_number].setPoint(0);
                Global.gameModels[Global.current_number].setTime(0);
            }

            Global.gameModels[Global.current_number].setAnswer(true);
            Global.gameModels[Global.current_number].setUser_answer(value);

        }

    }

    /**
     * end current game
     */
    public void endGame(View view) {

        // when click end button, show alert dialog.
        // if u press "SAVE", save current status of game and end game
        // and if press "Dont's Save", just end game.
        // and if press "Cancel", continue game.

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("End Game")
                .setMessage("Are you sure end game?")
                .setCancelable(false)
                .setNeutralButton(R.string.cancel, null)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopTimerTask();
                        saveGame();
                    }
                })
                .setNegativeButton(R.string.dont_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopTimerTask();
                        dialog.dismiss();
                        finish();
                    }
                });
        builder.show();
    }

    /**
     * save current game and end it
     */
    @SuppressLint("ApplySharedPref")
    private void saveGame() {

        // this is part that save game
        // already saved all game data in Global varialbe
        // it convert to json string and save shared preference.
        SharedPreferences preferences = getSharedPreferences("game", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("difficulty", Global.difficulty);
        editor.putBoolean("isHint", Global.isHint);
        editor.putInt("current_number", Global.current_number);
        editor.putString("content", new Gson().toJson(Global.gameModels));
        editor.commit();
        finish();

    }

    /**
     * start timer for time counter
     */
    private void startTimer() {

        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();

        // this is timer that delay is 1s and period is 1s
        // 1000ms = 1s
        timer.schedule(timerTask, 1000, 1000);
    }

    private void initializeTimerTask() {

        // generate handler
        final Handler handler = new Handler();

        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        setTimerEvent();
                    }
                });
            }
        };
    }

    private void stopTimerTask() {

        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void setTimerEvent() {

        // decrease remaining time
        remaining_time--;

        // if remaining time less that 0, go to next round or result page
        // otherwise just show current time
        if (remaining_time < 0) {

            Global.current_number++;
            stopTimerTask();

            if (Global.current_number < 10)  startActivity(new Intent(context, CalcActivity.class));
            else  startActivity(new Intent(context, ResultActivity.class));

            finish();
        } else {
            // display remaining time
            ((TextView) findViewById(R.id.text_remaining_time))
                    .setText(String.valueOf(remaining_time));
        }

    }

}
