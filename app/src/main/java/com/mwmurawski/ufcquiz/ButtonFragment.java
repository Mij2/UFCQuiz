package com.mwmurawski.ufcquiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class ButtonFragment extends Fragment {

    private static final String MODE = Const.MODE.getName();
    private String currentMode;

    private OnFragmentStartEndListener mCallback;

    @BindView(R.id.button) Button button;

    public ButtonFragment() {
        // Required empty public constructor
    }

    public static ButtonFragment newInstance(Const mode) {
        ButtonFragment fragment = new ButtonFragment();
        Bundle args = new Bundle();
        args.putString(MODE, mode.getName());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentMode = getArguments().getString(MODE);
        }

        if (currentMode != null){
            switch (currentMode){
                case Const.BUTTONS_START:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_button, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentStartEndListener) {
            mCallback = (OnFragmentStartEndListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentStartEndListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @OnClick(R.id.button)
    public void buttonClick(){
        mCallback.onButtonClick();
    }

    public interface OnFragmentStartEndListener {
        void onButtonClick();
    }
}
