package com.mwmurawski.ufcquiz.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.mwmurawski.ufcquiz.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Mij on 2017-02-28.
 */

public class Game implements Parcelable {

    private Integer numberOfQuestions;
    private Realm realm;
    private List<Questionable> listOfQuestions;
    private Stack<Questionable> stackOfQuestions;
    private Integer score;

    private Const gameState;

//    protected Game(Parcel in) {
//        numberOfQuestions = in.readInt();
//        stackOfQuestions = in.readli
//        parcel.writeList(stackOfQuestions);
//        parcel.writeInt(score);
//        parcel.writeString(gameState.getName());
//    }
//
//    public static final Creator<Game> CREATOR = new Creator<Game>() {
//        @Override
//        public Game createFromParcel(Parcel in) {
//            return new Game(in);
//        }
//
//        @Override
//        public Game[] newArray(int size) {
//            return new Game[size];
//        }
//    };

    public Const getGameState() {
        return gameState;
    }

    public void setGameState(Const gameState) {
        this.gameState = gameState;
    }

    public Game() {
        realm = Realm.getDefaultInstance();
        score = 0;
        initGame();
        setGameState(Const.GAME_STATE_START);
        setNumberOfQuestions();
    }

    private void setNumberOfQuestions() {
        numberOfQuestions = listOfQuestions.size();
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    private void initGame() {
        listOfQuestions = getQuestions();
        stackOfQuestions = new Stack<>();
        Collections.shuffle(listOfQuestions);
//        stackOfQuestions.addAll(listOfQuestions);
        stackOfQuestions.add(listOfQuestions.get(0)); //FIXME Debug mode
    }

    public Questionable getNextQuestion() {
        return stackOfQuestions.pop();
    }

    private List<Questionable> getQuestions() {
        RealmResults<Question> result = realm.where(Question.class).findAll();
        List<Question> questionList = realm.copyFromRealm(result);
        List<Questionable> questionableList = new ArrayList<>();
        questionableList.addAll(questionList);
        return questionableList;
//        return realm.copyFromRealm(result);
    }

    public void incrementScore(){
        score++;
    }

    public Integer getScore(){
        return score;
    }

    public boolean isQuestionStackEmpty(){
        return stackOfQuestions.isEmpty();
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(numberOfQuestions);
//        parcel.writeList(stackOfQuestions);
//        parcel.writeInt(score);
//        parcel.writeString(gameState.getName());
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.numberOfQuestions);
        dest.writeList(this.listOfQuestions);
        dest.writeList(this.stackOfQuestions);
        dest.writeValue(this.score);
        dest.writeInt(this.gameState == null ? -1 : this.gameState.ordinal());
    }

    protected Game(Parcel in) {
        this.numberOfQuestions = (Integer) in.readValue(Integer.class.getClassLoader());
        this.listOfQuestions = new ArrayList<Questionable>();
        in.readList(this.listOfQuestions, Questionable.class.getClassLoader());
        this.stackOfQuestions = new Stack<Questionable>();
        in.readList(this.stackOfQuestions, Questionable.class.getClassLoader());
        this.score = (Integer) in.readValue(Integer.class.getClassLoader());
        int tmpGameState = in.readInt();
        this.gameState = tmpGameState == -1 ? null : Const.values()[tmpGameState];
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
