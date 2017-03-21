package com.mwmurawski.ufcquiz;

/**
 * Created by Mij on 2017-03-11.
 */

public enum Const {
    STATE("state"),

    BUTTONS_START("buttons_start"),
    BUTTONS_END("buttons_end"),
    BUTTONS_NEXT("buttons_next"),

    GAME_STATE_START("game_state_start"),
    GAME_STATE_PLAYING("game_state_playing"),
    GAME_STATE_END("game_state_end"),

    RESULT("result"),
    NUMBER_OF_QUESTIONS("number_of_questions"),

    RB1("rb1"),
    RB2("rb2"),
    RB3("rb3"),
    RB4("rb4");
    ;

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
