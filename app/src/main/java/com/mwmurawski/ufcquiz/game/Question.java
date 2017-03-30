package com.mwmurawski.ufcquiz.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Question extends RealmObject implements Questionable{
    private String question;
    private String rightAnswer;
    private String answer1;
    private String answer2;
    private String answer3;

    @Ignore
    private List<String> listOfQuestions;

    public Question(){
        //needed default constructor
    }

    public Question(String question, String rightAnswer, String answer1, String answer2, String answer3) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    @Override
    public String getQuestion(){
        return question;
    }

    @Override
    public List<String> getShuffledAnswers(){
        List<String> list = new ArrayList<>(2);
        list.add(rightAnswer);
        list.add(answer1);
        list.add(answer2);
        list.add(answer3);
        Collections.shuffle(list);
        listOfQuestions = list;
        return list;
    }

    @Override
    public List<String> getAnswers(){
        return listOfQuestions == null ? getShuffledAnswers() : listOfQuestions;
    }

    @Override
    public Integer getCorrectAnswerId(List<String> list){
        return list.indexOf(rightAnswer);
    }
}
