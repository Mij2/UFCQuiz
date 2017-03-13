package com.mwmurawski.ufcquiz.game;

import java.util.List;


public interface Questionable {
    public String getQuestion();
    public String getRightAnswer();
    public List<String> getAnswers();
}
