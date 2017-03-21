package com.mwmurawski.ufcquiz;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mwmurawski.ufcquiz.fragment.ButtonFragment;
import com.mwmurawski.ufcquiz.fragment.GameFragment;
import com.mwmurawski.ufcquiz.fragment.MainWindowFragment;
import com.mwmurawski.ufcquiz.game.Game;
import com.mwmurawski.ufcquiz.game.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements GameFragment.OnFragmentGameListener, ButtonFragment.OnFragmentStartEndListener {

    private final Integer DELAY = 500;

    GameFragment.OnFragmentGameListener gameFragmentListener;
    ButtonFragment.OnFragmentStartEndListener startendFragmentListener;
    Realm realm;

//    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ButtonFragment buttonFragment;
//    MainWindowFragment mainWindowFragment;
    GameFragment gameFragment;

    @BindView(R.id.game_window) Fragment gameWindow;
    @BindView(R.id.button_window) Fragment buttonWindow;

    Game game;
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            configureFirstOpen();
        } else {
            Const mode = (Const) savedInstanceState.get(Const.STATE.getName());
            if (mode != null)
                restoreView(mode);
        }
    }

    private void restoreView(Const mode) {
        switch (mode) {
            case GAME_STATE_START: //TODO repair
                int i;
                break;
        }
    }



    private void configureFirstOpen() {
        //Realm configuration
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();

        //fragment instances
        MainWindowFragment mainWindowFragment = MainWindowFragment.newInstance(Const.GAME_STATE_START);
        buttonFragment = ButtonFragment.newInstance(Const.BUTTONS_START);

        //fragment configuration
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.game_window, mainWindowFragment);
        fragmentTransaction.add(R.id.button_window, buttonFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public String onButtonClick(final String mode) {
        switch (Const.valueOf(mode)){
            case BUTTONS_START:
                //Game configuration
                game = new Game();
                gameFragment = GameFragment.newInstance();
                question = game.getNextQuestion();
                gameFragment.setGameQuestion(question);
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.game_window, gameFragment);
                fragmentTransaction.commit();
                game.setGameState(Const.GAME_STATE_PLAYING);
                return Const.BUTTONS_NEXT.getName();
            case BUTTONS_NEXT:
                //todo check if there are questions
                if (game.isQuestionStackEmpty()){
                    MainWindowFragment endFragment = MainWindowFragment.newInstance(Const.GAME_STATE_END, game.getScore(), game.getNumberOfQuestions());
                    ButtonFragment buttonFragment = ButtonFragment.newInstance(Const.BUTTONS_END);
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.game_window, endFragment);
                    fragmentTransaction.add(R.id.button_window, buttonFragment);
                    fragmentTransaction.commit();
                    game.setGameState(Const.GAME_STATE_END);
                    return Const.BUTTONS_END.getName();
                }else {
                    //TODO check correct answer //TODO count score
                    if (isRightAnswerSelected(question, gameFragment)) game.incrementScore();
                    gameFragment.color();
                    question = game.getNextQuestionWithDelay(DELAY);
                    gameFragment.setGameQuestion(question);
                    gameFragment.colorDefault();
                    return Const.BUTTONS_NEXT.getName();
                }
            case BUTTONS_END:
                MainWindowFragment startFragment = MainWindowFragment.newInstance(Const.GAME_STATE_START);
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.game_window, startFragment);
                fragmentTransaction.commit();

                game.setGameState(Const.GAME_STATE_START);
                return Const.BUTTONS_START.getName();
        }
        Log.e("Illegal state", "Wrong state, check button code.");
        return Const.BUTTONS_START.getName(); //should never be execute
    }


    private boolean isRightAnswerSelected(Question question, GameFragment fragment){
        return question.getCorrectAnswerId().equals(fragment.getSelectedRadioButtonId());
    }
}
