package com.example.andreasnordstrom.madcw1.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by  Bror Nordstrom on 2018-02-28.
 * global function and utilities managing class
 */

public class Utils {

    //-----------Toast Functions---------------------------------

    public static void showShortToast(Context context, int res_id){
        Toast.makeText(context, res_id, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
