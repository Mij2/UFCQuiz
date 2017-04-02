package com.mwmurawski.ufcquiz.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mwmurawski.ufcquiz.R;
import com.mwmurawski.ufcquiz.game.Questionable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GameFragment extends Fragment {

    //butterknife
    private Unbinder unbinder;

    //views
    @BindView(R.id.question) TextView questionText;
    @BindView(R.id.answer1) RadioButton answer1;
    @BindView(R.id.answer2) RadioButton answer2;
    @BindView(R.id.answer3) RadioButton answer3;

    @BindView(R.id.answer4) RadioButton answer4;

    @BindView(R.id.radioButtonGroup) RadioGroup radioGroup;
    //vars
    private int rightColor;
    private int wrongColor;

    private int defaultColor;

    private Integer correctId;

    private OnFragmentGameListener mCallback;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {
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
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        unbinder = ButterKnife.bind(this, view);
        //Colors handlers
        defaultColor = answer1.getCurrentTextColor();
        answer1.getTextColors();
        //noinspection deprecation
        rightColor = getResources().getColor(R.color.right);
        //noinspection deprecation
        wrongColor = getResources().getColor(R.color.wrong);

        return view;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Sets question and answers to radiobuttons
     * @param question Question object
     */
    public void setGameQuestion(final Questionable question) {
        questionText.setText(question.getQuestion());
        List<String> answers = question.getAnswers();
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
        correctId = question.getCorrectAnswerId(answers);
    }

    /**
     * Sets radiobuttons to default color
     */
    public void colorDefault() {
        answer1.setTextColor(defaultColor);
        answer2.setTextColor(defaultColor);
        answer3.setTextColor(defaultColor);
        answer4.setTextColor(defaultColor);
    }

    /**
     * Sets color of selected answer,
     * sets RED when answer is wrong,
     * sets GREEN when answer is right
     */
    public void colorSelectedAnswer() {
        int selectedRadioButton = getSelectedRadioButtonId();
        int rightWrong = (correctId == getSelectedRadioButtonId()) ? rightColor : wrongColor;

        if      (selectedRadioButton == 0) answer1.setTextColor(rightWrong);
        else if (selectedRadioButton == 1) answer2.setTextColor(rightWrong);
        else if (selectedRadioButton == 2) answer3.setTextColor(rightWrong);
        else if (selectedRadioButton == 3) answer4.setTextColor(rightWrong);
    }

    /**
     * Deselects radiobutton
     */
    public void deselectRadioGroup() {
        radioGroup.clearCheck();
    }

    /**
     * Get id of selected button, first is 0, second 1, etc.
     * @return index of selected radiobutton
     */
    public int getSelectedRadioButtonId() {
        return radioGroup.indexOfChild(getActivity().findViewById(radioGroup.getCheckedRadioButtonId()));
    }

    /**
     * Checks if any of radiobutton is selected
     * @return true if there is no selected radiobutton, if there is selection, returns false
     */
    public boolean isSelectionEmpty(){
        return radioGroup.getCheckedRadioButtonId() == -1;
    }

    public interface OnFragmentGameListener {
        //there is no communication between fragment -> activity
    }
}
