package com.mwmurawski.ufcquiz;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mwmurawski.ufcquiz.game.Game;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements GameFragment.OnFragmentGameListener, ButtonFragment.OnFragmentStartEndListener {

    GameFragment.OnFragmentGameListener gameFragmentListener;
    ButtonFragment.OnFragmentStartEndListener startendFragmentListener;
    Realm realm;

    ButtonFragment buttonFragment;
    MainWindowFragment mainWindowFragment;
    GameFragment gameFragment;

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

        //fragment configuration


        //Game configuration
        Game game = new Game();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onButtonClick() {

    }
}
