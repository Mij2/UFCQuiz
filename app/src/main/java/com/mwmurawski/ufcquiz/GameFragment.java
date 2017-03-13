package com.mwmurawski.ufcquiz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;

public class GameFragment extends Fragment {

    @BindView(R.id.question) TextView question;
    @BindView(R.id.answer1) RadioButton answer1;
    @BindView(R.id.answer2) RadioButton answer2;
    @BindView(R.id.answer3) RadioButton answer3;
    @BindView(R.id.answer4) RadioButton answer4;

    private OnFragmentGameListener mCallback;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        //arguments if needed
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mCallback != null) {
            mCallback.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentGameListener) {
            mCallback = (OnFragmentGameListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentGameListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnFragmentGameListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
