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
import com.mwmurawski.ufcquiz.game.Question;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GameFragment extends Fragment {

    @BindView(R.id.question) private TextView questionText;
    @BindView(R.id.answer1) private RadioButton answer1;
    @BindView(R.id.answer2) private RadioButton answer2;
    @BindView(R.id.answer3) private RadioButton answer3;
    @BindView(R.id.answer4) private RadioButton answer4;

    @BindView(R.id.radioButtonGroup) private RadioGroup radioGroup;

    private int rightColor;
    private int wrongColor;
    private int defaultColor;

    private Integer correctId;

    private OnFragmentGameListener mCallback;

    private Unbinder unbinder;

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

        defaultColor = answer1.getCurrentTextColor();
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

    public void setGameQuestion(final Question question){
        questionText.setText(question.getQuestion());
        List<String> answers = question.getAnswers();
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
        correctId = question.getCorrectAnswerId();
    }

    public void colorDefault(){
        answer1.setTextColor(defaultColor);
        answer2.setTextColor(defaultColor);
        answer3.setTextColor(defaultColor);
        answer4.setTextColor(defaultColor);
    }

    public void color(){
        Boolean rightAnswer = (correctId == getSelectedRadioButtonId());
        if (answer1.isSelected()) answer1.setTextColor(rightAnswer ? rightColor : wrongColor);
        if (answer2.isSelected()) answer2.setTextColor(rightAnswer ? rightColor : wrongColor);
        if (answer3.isSelected()) answer3.setTextColor(rightAnswer ? rightColor : wrongColor);
        if (answer4.isSelected()) answer4.setTextColor(rightAnswer ? rightColor : wrongColor);
    }


    public int getSelectedRadioButtonId(){
        return radioGroup.getCheckedRadioButtonId();
    }

    public interface OnFragmentGameListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
