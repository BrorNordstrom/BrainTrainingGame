package com.example.andreasnordstrom.madcw1.util;

import com.example.andreasnordstrom.madcw1.model.GameModel;

/**
 * Created by  Bror Nordstrom on 2018-02-28.
 * global constant and variable managing class
 *
 * this is global class
 *
 */

public class Global {

    // this is round count
    public static final int TOTAL_COUNT = 10;

    // this is total time
    public static final int TOTAL_TIME = 10;
    public static final String[] operators = new String[]{"+", "-", "*", "/"};

    // game difficulty
    public static int difficulty;

    // hint option status
    public static boolean isHint;

    // all question array
    public static GameModel[] gameModels = new GameModel[TOTAL_COUNT];

    // initial game number
    public static int current_number = 0;
    //User starts new game, = 1
    //Continue game = this is saved final round number

}
