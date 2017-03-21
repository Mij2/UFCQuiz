package com.mwmurawski.ufcquiz.game;

import android.os.Handler;

import com.mwmurawski.ufcquiz.Const;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Mij on 2017-02-28.
 */

public class Game {

    private Integer numberOfQuestions;
    private Realm realm;
    private Questionable quiz;
    private List<Question> listOfQuestions;
    private Stack<Question> stackOfQuestions;
    private Integer score;
    private Handler handler;

    private Const gameState;

    public Const getGameState() {
        return gameState;
    }

    public void setGameState(Const gameState) {
        this.gameState = gameState;
    }

    public Game() {
        realm = Realm.getDefaultInstance();
        score = 0;
        initGame();
        handler = new Handler();
        setGameState(Const.GAME_STATE_START);
        setNumberOfQuestions();
    }

    private void setNumberOfQuestions() {
        numberOfQuestions = listOfQuestions.size();
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    private void initGame() {
        listOfQuestions = getQuestions();
        Collections.shuffle(listOfQuestions);
        stackOfQuestions.addAll(listOfQuestions);
    }

    public Question getNextQuestion() {
        return stackOfQuestions.pop();
    }

    public Question getNextQuestionWithDelay(Integer delay){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //just wait a delay time
            }
        }, delay);
        return getNextQuestion();
    }

    private List<Question> getQuestions() {
        RealmResults<Question> result = realm.where(Question.class).findAll();
        return realm.copyFromRealm(result);
    }

    public void incrementScore(){
        score++;
    }

    public Integer getScore(){
        return score;
    }

    public boolean isQuestionStackEmpty(){
        return stackOfQuestions.isEmpty();
    }
}
