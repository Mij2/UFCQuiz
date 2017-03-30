package com.mwmurawski.ufcquiz.game;

import java.util.List;


public interface Questionable {
    public String getQuestion();
    public List<String> getShuffledAnswers();
    public Integer getCorrectAnswerId(List<String> list);
    public List<String> getAnswers();
}
