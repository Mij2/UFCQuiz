package com.mwmurawski.ufcquiz;

/**
 * Created by Mij on 2017-03-11.
 */

public enum Const {
    MODE("mode"),
    BUTTONS_START("buttons_start"),
    BUTTONS_END("buttons_end"),
    BUTTONS_NEXT("buttons_next"),
    CONTENT_START("content_start"),
    CONTENT_GAME("content_game"),
    CONTENT_END("content_end"),
    MODE_START("mode_start"),
    MODE_GAME("mode_game"),
    MODE_END("mode_end"),
    ;

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
