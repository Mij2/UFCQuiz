package com.mwmurawski.ufcquiz.game;

import android.os.Parcelable;

import java.util.List;


public interface Questionable extends Parcelable{
    public String getQuestion();
    public List<String> getShuffledAnswers();
    public Integer getCorrectAnswerId(List<String> list);
    public List<String> getAnswers();
}
