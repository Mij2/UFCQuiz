package com.mwmurawski.ufcquiz.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Mij on 2017-02-28.
 */

public class Question extends RealmObject implements Questionable{
    private String question;
    private String rightAnswer;
    private String answer1;
    private String answer2;
    private String answer3;

    private List<String> answersList;

    public Question(){
        //needed default constructor
    }

    public Question(String question, String rightAnswer, String answer1, String answer2, String answer3) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;

        populateAnswersList(rightAnswer,answer1,answer2,answer3);
        shuffleAnswers();
    }

    private void shuffleAnswers(){
        Collections.shuffle(answersList);
    }

    private void populateAnswersList(String a1, String a2, String a3, String a4){
        answersList = new ArrayList<>(4);
        answersList.add(a1);
        answersList.add(a2);
        answersList.add(a3);
        answersList.add(a4);
    }

    @Override
    public String getQuestion(){
        return question;
    }

    @Override
    public String getRightAnswer(){
        return rightAnswer;
    }

    @Override
    public List<String> getAnswers(){
        return answersList;
    }

    public Integer getCorrectAnswerId(){
        return answersList.indexOf(rightAnswer);
    }
}
