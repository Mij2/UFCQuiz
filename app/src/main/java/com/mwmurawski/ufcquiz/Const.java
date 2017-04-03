package com.mwmurawski.ufcquiz;

//TODO Think about thic enum and maybe change it to class with static fields..
public enum Const {
    //state
    STATE("STATE"),

    //buttons state
    BUTTONS_START("BUTTONS_START"),
    BUTTONS_END("BUTTONS_END"),
    BUTTONS_NEXT("BUTTONS_NEXT"),

    //game state
    GAME_STATE_START("GAME_STATE_START"),
    GAME_STATE_PLAYING("GAME_STATE_PLAYING"),
    GAME_STATE_END("GAME_STATE_END"),

    //result consts
    RESULT("RESULT"),
    NUMBER_OF_QUESTIONS("NUMBER_OF_QUESTIONS"),

    //???
    RB1("RB1"),
    RB2("RB2"),
    RB3("RB3"),
    RB4("RB4"),

    //sharedPrefs consts
    DB_SAVED("DB_SAVED"),

    //fragment consts
    FRAG_BUTTON("FRAG_BUTTON"),
    FRAG_GAME("FRAG_GAME"),
    FRAG_MAIN("FRAG_MAIN"),

    //parcelable consts
    PARCELABLE_QUESTION("PARCELABLE_QUESTION"),
    PARCELABLE_GAME("PARCELABLE_GAME"),
    PARCELABLE_STATE("PARCELABLE_STATE"),

    FRAG_GAME_REMOVE("FRAG_GAME_REMOVE"),
    BUTTON_TAG("BUTTON_TAG");
    ;

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
