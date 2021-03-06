package com.mwmurawski.ufcquiz;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mwmurawski.ufcquiz.fragment.ButtonFragment;
import com.mwmurawski.ufcquiz.fragment.GameFragment;
import com.mwmurawski.ufcquiz.fragment.MainWindowFragment;
import com.mwmurawski.ufcquiz.game.DataInitializer;
import com.mwmurawski.ufcquiz.game.Game;
import com.mwmurawski.ufcquiz.game.Questionable;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.widget.Toast.LENGTH_SHORT;
import static com.mwmurawski.ufcquiz.Const.BUTTONS_NEXT;

public class MainActivity
        extends
        AppCompatActivity
        implements
        GameFragment.OnFragmentGameListener,
        ButtonFragment.OnFragmentStartEndListener,
        MainWindowFragment.OnFragmentInteractionListenerMainWindow {

    //const
    private final Integer DELAY = 500;

    //realm
    Realm realm;

    //communication fragment > activity
    GameFragment.OnFragmentGameListener gameFragmentListener;
    ButtonFragment.OnFragmentStartEndListener startendFragmentListener;

    //communication activity > fragment
    MainWindowFragment mainWindowFragment;
    GameFragment gameFragment;
    ButtonFragment buttonFragment;

    //game vars
    Game game;
    Questionable question;
    DataInitializer dataInitializer;

    String buttonState;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CONFIGURATION: ButterKnife
        ButterKnife.bind(this);

        //CONFIGURATION: Realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);

        handler = new Handler();

        if (savedInstanceState == null) {
            configureFirstOpen();
        } else {
            buttonState = savedInstanceState.getString(Const.PARCELABLE_STATE.getName());
            game = savedInstanceState.getParcelable(Const.PARCELABLE_GAME.getName());
            question = savedInstanceState.getParcelable(Const.PARCELABLE_QUESTION.getName());

            if (buttonState != null)
                restoreView(buttonState);
        }
    }

    /**
     * Restore existing views after screen rotation
     * @param buttonState state of a game i.e. button
     */
    private void restoreView(String buttonState) {
        switch (Const.valueOf(buttonState)){
            case BUTTONS_NEXT:
                gameFragment = (GameFragment) getFragmentManager().findFragmentById(R.id.game_window);
                buttonFragment = (ButtonFragment) getFragmentManager().findFragmentById(R.id.button_window);
                break;
            case BUTTONS_START:
            case BUTTONS_END:
                mainWindowFragment = (MainWindowFragment) getFragmentManager().findFragmentById(R.id.game_window);
                buttonFragment = (ButtonFragment) getFragmentManager().findFragmentById(R.id.button_window);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {

            if (buttonFragment.isButtonSetToStart())
                super.onBackPressed();

            game = null;
            ButtonFragment buttonFragment = (ButtonFragment) getFragmentManager().findFragmentById(R.id.button_window);
            buttonFragment.changeCurrentModeString(Const.BUTTONS_START.getName());

            //TODO NOT BEST APROACH.. BUT IT'S WORKING (almost)

            MainWindowFragment startFragment = MainWindowFragment.newInstance(Const.GAME_STATE_START);
            getFragmentManager().beginTransaction()
                    .replace(R.id.game_window, startFragment, Const.FRAG_MAIN.getName())
                    .commit();
//            getFragmentManager().popBackStack();
        }
    }

    private void configureFirstOpen() {
        //Realm configuration
        realm = Realm.getDefaultInstance();
        dataInitializer = new DataInitializer();
        dataInitializer.initialize();

        //fragments initialization
        mainWindowFragment = MainWindowFragment.newInstance(Const.GAME_STATE_START);
        buttonFragment = ButtonFragment.newInstance(Const.BUTTONS_START);

        //fragment configuration
        getFragmentManager()
                .beginTransaction()
                .add(R.id.game_window, mainWindowFragment, Const.FRAG_MAIN.getName())
                .addToBackStack(null)
                .commit();

        //one add to back stack, one not..
        //TODO but it not works perfect
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.button_window, buttonFragment, Const.BUTTON_TAG.getName())
                .commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Const.PARCELABLE_QUESTION.getName(), question);
        outState.putParcelable(Const.PARCELABLE_GAME.getName(), game);

        outState.putString(Const.PARCELABLE_STATE.getName(), buttonState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public String onButtonClick(final String mode) {
        buttonState = null;
        switch (Const.valueOf(mode)) {

            // ================================================ BUTTON_START CASE

            case BUTTONS_START:

                //game config
                game = new Game();

                //fragment config
                gameFragment = GameFragment.newInstance();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.game_window, gameFragment, Const.FRAG_GAME_REMOVE.getName())
                        .commit();

                //executes transactions immediately
                getFragmentManager().executePendingTransactions();

                //question config
                question = game.getNextQuestion();
                gameFragment.setGameQuestion(question);

                //change game state
                game.setGameState(Const.GAME_STATE_PLAYING);

                buttonState = BUTTONS_NEXT.getName();
                break;

            // ================================================ BUTTON_NEXT CASE

            case BUTTONS_NEXT:

                if (gameFragment.isSelectionEmpty()) {
                    buttonState = BUTTONS_NEXT.getName(); //cause var is nulled in the beginning
                    Toast.makeText(this, "Select answer!", LENGTH_SHORT).show();
                    break;
                }
                //check stack
                //question config
                if (isRightAnswerSelected(question, gameFragment)) game.incrementScore();

                gameFragment.colorSelectedAnswer();
                buttonFragment.disableButton();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (game.isQuestionStackEmpty()) {
                            //info: END OF GAME
                            //fragment config

                            MainWindowFragment endFragment = MainWindowFragment.newInstance(Const.GAME_STATE_END, game.getScore(), game.getNumberOfQuestions());
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.game_window, endFragment)
                                    .commit();

                            buttonFragment.enableButton();
                            //change game state
                            game.setGameState(Const.GAME_STATE_END);
                        } else {

                            //info: NEXT QUESTION CONFIG
                            question = game.getNextQuestion();
                            gameFragment.setGameQuestion(question);
                            gameFragment.colorDefault();
                            gameFragment.deselectRadioGroup();
                            buttonFragment.enableButton();
                        }
                    }
                }, DELAY);

                //change button state (depends on if stack is empty)
                buttonState = game.isQuestionStackEmpty() ? Const.BUTTONS_END.getName() : BUTTONS_NEXT.getName();
                break;

            // ================================================ BUTTON_END CASE

            case BUTTONS_END:
                //fragment config
                MainWindowFragment startFragment = MainWindowFragment.newInstance(Const.GAME_STATE_START);
                getFragmentManager().beginTransaction()
                        .replace(R.id.game_window, startFragment, Const.FRAG_MAIN.getName())
                        .commit();

                //change game state
                game.setGameState(Const.GAME_STATE_START);
                buttonState = Const.BUTTONS_START.getName();
        }
        return buttonState;
    }


    private boolean isRightAnswerSelected(Questionable question, GameFragment fragment) {
        return question.getCorrectAnswerId(question.getAnswers()).equals(fragment.getSelectedRadioButtonId());
    }
}
