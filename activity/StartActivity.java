package com.example.andreasnordstrom.madcw1.activity;

/**
 * Created by  Bror Nordstrom on 2018-02-28.
 * game managing model
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.andreasnordstrom.madcw1.R;
import com.example.andreasnordstrom.madcw1.model.GameModel;
import com.example.andreasnordstrom.madcw1.util.Global;
import com.example.andreasnordstrom.madcw1.util.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StartActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        context = this;
    }

    /**
     * start new game
     * when click start button, go to setting page for start game
     */
    public void startGame(View view) {
        startActivity(new Intent(context, SettingActivity.class));
    }

    /**
     * continue saving game in previous time
     */
    public void continueGame(View view) {

        // and this is continue game
        // get old game data from shared preference

        SharedPreferences preferences = getSharedPreferences("game", Context.MODE_PRIVATE);

        try {

            Global.difficulty = preferences.getInt("difficulty", 0);
            Global.isHint = preferences.getBoolean("isHint", false);
            Global.current_number = preferences.getInt("current_number", 0);

            JSONArray array = new JSONArray(preferences.getString("content", ""));

            if (Global.difficulty == 0 || array.length() == 0) {
                Utils.showShortToast(context, "No Saved Data.");
                return;
            }

            // get all game data and start game from that number

            for (int i = 0; i < Global.TOTAL_COUNT; i++)
                Global.gameModels[i] = new Gson().fromJson(array.getString(i), GameModel.class);

            startActivity(new Intent(context, CalcActivity.class));

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showShortToast(context, "No Saved Data.");
        }

    }

    /**
     * show about game in popup dialog
     * when click about game button, show content in dialog
     * get content from raw file
     */
    public void about(View view) {

        // get about text from raw file
        InputStream inputStream = getResources().openRawResource(R.raw.about);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String content = "";
        int in;
        try {
            in = inputStream.read();
            while (in != -1)
            {
                byteArrayOutputStream.write(in);
                in = inputStream.read();
            }
            inputStream.close();
            content = byteArrayOutputStream.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }

        // show dialog with about text
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.about)
                .setMessage(content)
                .setPositiveButton(R.string.cancel, null);
        builder.show();

    }

    /**
     * exit game function
     * when click exit game button, exit game
     */
    public void exitGame(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.exit_game)
                .setMessage("Are you sure exit game?")
                .setCancelable(false)
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, null);
        builder.show();

    }
}
