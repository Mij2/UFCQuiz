package com.mwmurawski.ufcquiz.game;

import com.mwmurawski.ufcquiz.Const;

import java.util.ArrayList;
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
    private List<Questionable> listOfQuestions;
    private Stack<Questionable> stackOfQuestions;
    private Integer score;

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
        stackOfQuestions = new Stack<>();
        Collections.shuffle(listOfQuestions);
        stackOfQuestions.addAll(listOfQuestions);
//        stackOfQuestions.add(listOfQuestions.get(0)); //FIXME Debug mode
    }

    public Questionable getNextQuestion() {
        return stackOfQuestions.pop();
    }

    private List<Questionable> getQuestions() {
        RealmResults<Question> result = realm.where(Question.class).findAll();
        List<Question> questionList = realm.copyFromRealm(result);
        List<Questionable> questionableList = new ArrayList<>();
        questionableList.addAll(questionList);
        return questionableList;
//        return realm.copyFromRealm(result);
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
