package com.mwmurawski.ufcquiz;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements GameFragment.OnFragmentInteractionListener{

    GameFragment.OnFragmentInteractionListener gameFragment;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
