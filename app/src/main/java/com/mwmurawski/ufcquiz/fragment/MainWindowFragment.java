package com.mwmurawski.ufcquiz.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwmurawski.ufcquiz.Const;
import com.mwmurawski.ufcquiz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainWindowFragment extends Fragment {

    private static final String MODE = Const.STATE.getName();

    private String currentModeString;
    private Integer result;

    private Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.main_string) TextView mainWindowString;

    public MainWindowFragment() {
        // Required empty public constructor
    }

    public static MainWindowFragment newInstance(Const mode) {
        MainWindowFragment fragment = new MainWindowFragment();
        fragment.setMainText("WELCOME IN\nUFC QUIZ");
        Bundle args = new Bundle();
        args.putString(MODE, mode.getName());
        fragment.setArguments(args);
        return fragment;
    }

    public static MainWindowFragment newInstance(Const mode, Integer result, Integer numberOfQuestions) {
        MainWindowFragment fragment = new MainWindowFragment();
        fragment.setMainText("RESULT OF YOUR GAME: \n" + result + " / "+ numberOfQuestions);
        Bundle args = new Bundle();
        args.putString(MODE, mode.getName());
        args.putInt(Const.RESULT.getName(), result);
        args.putInt(Const.NUMBER_OF_QUESTIONS.getName(), numberOfQuestions);
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
        View view = inflater.inflate(R.layout.fragment_main_window, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            currentModeString = getArguments().getString(MODE);
            result = getArguments().getInt(Const.RESULT.getName());
        }

        if (currentModeString != null) {
            switch (Const.valueOf(currentModeString)) {
                case GAME_STATE_START:
                    setMainText("START GAME");
                    break;
                case GAME_STATE_END:
                    if (result != null) {
                        setMainText("Result: " + result);
                    }else{
                        setMainText("Result: ERROR"); //shouldn't execute
                    }
                    break;
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setMainText(String text){
        mainWindowString.setText(text);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
