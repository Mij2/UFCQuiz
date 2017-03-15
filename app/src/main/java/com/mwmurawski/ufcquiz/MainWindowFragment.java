package com.mwmurawski.ufcquiz;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

public class MainWindowFragment extends Fragment {

    private static final String MODE = Const.MODE.getName();

    private String currentModeString;
    private String resultString;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.main_window_string) TextView mainWindowString;

    public MainWindowFragment() {
        // Required empty public constructor
    }

    public static MainWindowFragment newInstance(Const mode) {
        MainWindowFragment fragment = new MainWindowFragment();
        Bundle args = new Bundle();
        args.putString(MODE, mode.getName());
        fragment.setArguments(args);
        return fragment;
    }

    public static MainWindowFragment newInstance(Const mode, String result) {
        MainWindowFragment fragment = new MainWindowFragment();
        Bundle args = new Bundle();
        args.putString(MODE, mode.getName());
        args.putString(Const.RESULT.getName(), result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentModeString = getArguments().getString(MODE);
            resultString = getArguments().getString(Const.RESULT.getName());
        }

        if (currentModeString != null) {
            switch (Const.valueOf(currentModeString)) {
                case CONTENT_START:
                   setMainText("START GAME");
                    break;
                case CONTENT_END:
                    if (resultString != null) {
                        setMainText("Result: " + resultString);
                    }else{
                        setMainText("Result: ERROR"); //shouldn't execute
                    }
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_window, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
