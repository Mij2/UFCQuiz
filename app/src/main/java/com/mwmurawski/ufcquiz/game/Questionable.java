package com.mwmurawski.ufcquiz.game;

import android.os.Parcelable;

import java.util.List;


public interface Questionable extends Parcelable{

    /**
     * Gets current question string, actual question
     * @return a question
     */
    public String getQuestion();

    /**
     * Gets shuffled list of answers
     * @return shuffled answer list
     */
    public List<String> getShuffledAnswers();

    /**
     * Takes list of string answers and gets index of correct answer in a list, later it is compared to selected radiobutton id.
     * @param list list of answers
     * @return index of correct answer
     */
    public Integer getCorrectAnswerId(List<String> list);

    /**
     * Gets answers of a question
      * @return answers
     */
    public List<String> getAnswers();
}
