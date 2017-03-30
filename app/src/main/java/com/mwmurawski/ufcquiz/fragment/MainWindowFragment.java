package com.mwmurawski.ufcquiz.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

    //const
    private static final String MODE = Const.STATE.getName();

    //views
    @BindView(R.id.main_string) TextView mainWindowString;

    ///vars
    private Unbinder unbinder; //ButterKnife
    private String currentModeString;
    private Integer result;
    private Integer nrOfQuestions;
    private OnFragmentInteractionListenerMainWindow mListener;

    public MainWindowFragment() {
        // Required empty public constructor
    }

    /**
     * New instance that provides main window fragment
     * @param mode Mode of window
     * @return Returns fragment
     */
    public static MainWindowFragment newInstance(Const mode) {
        MainWindowFragment fragment = new MainWindowFragment();
        Bundle args = new Bundle();
        args.putString(MODE, mode.getName());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * New instance that provides main window fragment with result
     * @param mode Mode of window
     * @param result - Result of game
     * @param numberOfQuestions Number of questions in a game
     * @return Returns fragment with result
     */
    public static MainWindowFragment newInstance(Const mode, Integer result, Integer numberOfQuestions) {
        MainWindowFragment fragment = new MainWindowFragment();
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

        //ButterKnife
        unbinder = ButterKnife.bind(this, view);

        //checks arguments initialized in #newInstance()
        if (getArguments() != null) {
            currentModeString = getArguments().getString(MODE);
            result = getArguments().getInt(Const.RESULT.getName());
            nrOfQuestions = getArguments().getInt(Const.NUMBER_OF_QUESTIONS.getName());
        }

        //Switch creating view dependently on mode
        if (currentModeString != null) {
            switch (Const.valueOf(currentModeString)) {
                case GAME_STATE_START:
                    mainWindowString.setText(R.string.main_window_welcome);
                    break;

                case GAME_STATE_END:
                    if (result != null && nrOfQuestions != null) {
                        mainWindowString.setText(getString(R.string.main_window_result) + result + " / " + nrOfQuestions);
                    } else {
                        Log.e("RESULT ERROR", "result or nrOfQuestions is set to null");
                        mainWindowString.setText(R.string.main_window_error); //shouldn't execute
                    }
                    break;
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind(); //unbinds butterknife
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListenerMainWindow) {
            mListener = (OnFragmentInteractionListenerMainWindow) context;
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

    public interface OnFragmentInteractionListenerMainWindow {
        //there is no communication between fragment -> activity
    }
}
