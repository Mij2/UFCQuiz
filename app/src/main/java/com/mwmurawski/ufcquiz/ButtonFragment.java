package com.mwmurawski.ufcquiz;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class ButtonFragment extends Fragment {

    private static final String MODE = Const.MODE.getName();
    private String currentModeString;

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
            currentModeString = getArguments().getString(MODE);
        }

        if (currentModeString != null){

            switch (Const.valueOf(currentModeString)){
                case BUTTONS_START:
                    button.setText("START");
                    break;
                case BUTTONS_NEXT:
                    button.setText("NEXT QUESTION");
                    break;
                case BUTTONS_END:
                    button.setText("RESTART");
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
        mCallback.onButtonClick(currentModeString);
    }

    public interface OnFragmentStartEndListener {
        void onButtonClick(String mode);
    }
}
