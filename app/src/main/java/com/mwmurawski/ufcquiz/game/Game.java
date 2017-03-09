package com.mwmurawski.ufcquiz.game;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Mij on 2017-02-28.
 */

public class Game {

    Realm realm;
    Questionable quiz;
    List<Question> listOfQuestions;
    Stack<Question> stackOfQuestions;

    public Game() {
        realm = Realm.getDefaultInstance();
    }

    public Question initGame() {
        listOfQuestions = getQuestions();
        Collections.shuffle(listOfQuestions);
        stackOfQuestions.addAll(listOfQuestions);
        return stackOfQuestions.pop();
    }

    public Question getNextQuestion(){
        return stackOfQuestions.pop();
    }


    private List<Question> getQuestions(){
        RealmResults<Question> result =  realm.where(Question.class).findAll();
        return realm.copyFromRealm(result);
    }
}
