package com.mwmurawski.ufcquiz;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mwmurawski.ufcquiz.game.Game;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements GameFragment.OnFragmentGameListener, ButtonFragment.OnFragmentStartEndListener {

    GameFragment.OnFragmentGameListener gameFragmentListener;
    ButtonFragment.OnFragmentStartEndListener startendFragmentListener;
    Realm realm;

//    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ButtonFragment buttonFragment;
    MainWindowFragment mainWindowFragment;
    GameFragment gameFragment;

    @BindView(R.id.game_window) Fragment gameWindow;
    @BindView(R.id.button_window) Fragment buttonWindow;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            configureFirstOpen();
        } else {
            Const mode = (Const) savedInstanceState.get(Const.MODE.getName());
            if (mode != null)
                restoreView(mode);
        }
    }

    private void restoreView(Const mode) {
        switch (mode) {
            case CONTENT_END:
                int i = 5;
                break;
        }
    }

    private void configureFirstOpen() {
        //Realm configuration
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();

        //fragment instances
        mainWindowFragment = MainWindowFragment.newInstance(Const.MODE_START);
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
    public void onButtonClick(final String mode) {
        switch (Const.valueOf(mode)){
            case BUTTONS_START:
                //Game configuration
                game = new Game();
                gameFragment.setGameQuestion(game.getNextQuestion());
                //todo start game, set gamefragment

                break;
            case BUTTONS_NEXT:
                //TODO check correct answer
                //TODO count score
                //todo check if there are questions
                //get next question or get mainwindow fragment

                break;
            case BUTTONS_END:
                //todo go back to main window with welcome message
                break;
        }
    }
}
