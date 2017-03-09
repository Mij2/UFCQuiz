package com.mwmurawski.ufcquiz.game;

import java.util.ArrayList;
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
    public String getRightAnswer(){
        return rightAnswer;
    }

    @Override
    public List<String> getAnswers(){
        List<String> answers = new ArrayList<>();
        answers.add(rightAnswer);
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        return answers;
    }
}
