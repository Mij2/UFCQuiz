package com.mwmurawski.ufcquiz.game;

import java.util.List;

/**
 * Created by Mij on 2017-02-28.
 */

public interface Questionable {
    public String getQuestion();
    public String getRightAnswer();
    public List<String> getAnswers();
}
