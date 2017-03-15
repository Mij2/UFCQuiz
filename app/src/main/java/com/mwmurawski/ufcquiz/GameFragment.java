package com.mwmurawski.ufcquiz;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mwmurawski.ufcquiz.game.Question;

import java.util.List;

import butterknife.BindView;

public class GameFragment extends Fragment {

    @BindView(R.id.question) private TextView questionText;
    @BindView(R.id.answer1) private RadioButton answer1;
    @BindView(R.id.answer2) private RadioButton answer2;
    @BindView(R.id.answer3) private RadioButton answer3;
    @BindView(R.id.answer4) private RadioButton answer4;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
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

    public void setGameQuestion(Question question){
        questionText.setText(question.getQuestion());
        List<String> answers = question.getShuffledAnswers();
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
    }

    public interface OnFragmentGameListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
