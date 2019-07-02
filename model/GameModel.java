package com.example.andreasnordstrom.madcw1.model;

/**
 * Created by  Bror Nordstrom on 2018-02-28.
 * game managing model
 * this is model class for game managing
 */

public class GameModel {

    // expression
    private String expression;

    // correct answer of expression
    private int correct_answer;

    // answer status
    private boolean isAnswer;

    // answer of user
    private int user_answer;

    // score of round
    private int point;

    // calculation time
    private int time;


    // this is construction of game model
    public GameModel(String expression, int correct_answer, boolean isAnswer, int user_answer, int point, int time) {
        this.expression = expression;
        this.correct_answer = correct_answer;
        this.isAnswer = isAnswer;
        this.user_answer = user_answer;
        this.point = point;
        this.time = time;
    }


    // this is setter and getter of all private variable

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public int getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(int user_answer) {
        this.user_answer = user_answer;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
