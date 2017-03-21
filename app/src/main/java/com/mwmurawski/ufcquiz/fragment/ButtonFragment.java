package com.mwmurawski.ufcquiz.fragment;

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

    private static final String MODE = Const.STATE.getName();
    private String currentModeString;

    private Unbinder unbinder;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            currentModeString = getArguments().getString(MODE);
        }

        if (currentModeString != null) {
            changeButtonText(currentModeString);
        }

        return view;
    }

    public void changeButtonText(String mode){
        switch (Const.valueOf(mode)){
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

    public void disableButton() {
        button.setEnabled(false);
    }

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
        unbinder.unbind();
    }

    @OnClick(R.id.button)
    public void buttonClick(){
        currentModeString = mCallback.onButtonClick(currentModeString);
        changeButtonText(currentModeString);
    }

    public interface OnFragmentStartEndListener {
        String onButtonClick(String mode);
    }
}
