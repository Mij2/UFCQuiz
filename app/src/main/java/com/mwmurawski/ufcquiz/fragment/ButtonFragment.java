package com.mwmurawski.ufcquiz.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mwmurawski.ufcquiz.Const;
import com.mwmurawski.ufcquiz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ButtonFragment extends Fragment {

    //const
    private static final String MODE = Const.STATE.getName();

    //views
    @BindView(R.id.button) Button button;

    //butterknife
    private Unbinder unbinder;

    //vars
    private String currentModeString;
    private OnFragmentStartEndListener mCallback;

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
    }

    /**
     * Change mode of fragment, i.e. button
     * @param mode mode of fragment
     */
    public void changeCurrentModeString(String mode) {
        currentModeString = mode;
        setButtonText(mode);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Const.PARCELABLE_STATE.getName(),currentModeString);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRetainInstance(true);

        if (getArguments() != null) {
            currentModeString = getArguments().getString(MODE);
        }

        if (savedInstanceState != null){
            currentModeString = savedInstanceState.getString(Const.PARCELABLE_STATE.getName());
        }

        //sets button text, depends on mode set when instance was made
        if (currentModeString != null) {
            setButtonText(currentModeString);
        }

        return view;
    }

    /**
     * Sets button text, it depends on mode set in argument, there can be 3 modes: BUTTON_START, BUTTON_NEXT, BUTTON_END
     * @param currentMode mode of buttons
     */
    private void setButtonText(String currentMode){
        if (currentMode == null) return;
        switch (Const.valueOf(currentMode)){
            case BUTTONS_START:
            case GAME_STATE_START:
                button.setText("START");
                break;
            case BUTTONS_NEXT:
            case GAME_STATE_PLAYING:
                button.setText("NEXT QUESTION");
                break;
            case BUTTONS_END:
            case GAME_STATE_END:
                button.setText("RESTART");
                break;
        }
    }


    /**
     * We need it to work with older phones.. like mine phone for example :P
     * @param activity activity that sets fragment
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentStartEndListener) {
            mCallback = (OnFragmentStartEndListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentStartEndListener");
        }
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

    /**
     * Disable button
     */
    public void disableButton() {
        button.setEnabled(false);
    }

    /**
     * Enable button
     */
    public void enableButton() {
        button.setEnabled(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind(); //unbind ButterKnife
    }


    @OnClick(R.id.button)
    public void buttonClick(){
        currentModeString = mCallback.onButtonClick(currentModeString);
        setButtonText(currentModeString);
    }

    //interface needed to communicate with activity
    public interface OnFragmentStartEndListener {
        String onButtonClick(String mode);
    }

    public boolean isButtonSetToStart(){
        return button.getText().equals("START");
    }
}
