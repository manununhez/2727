package com.cibersons.app2727.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cibersons.app2727.R;

/**
 * Created by Manuel on 8/19/2015.
 */
public class HistorialFragment extends Fragment {

    private static HistorialFragment instance;


    OnHistorialListener mCallback;

    // Container Activity must implement this interface
    public interface OnHistorialListener {
        public void onGetHistorial();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

        Activity activity = null;

        if (context instanceof Activity){
            activity=(Activity) context;
        }
        try {
            mCallback = (OnHistorialListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHistorialListener");
        }
    }
    public static HistorialFragment newInstance(String title) {
        if (instance == null) {
            instance = new HistorialFragment();
            Bundle args = new Bundle();
//            args.putString(ARG_FRAGMENT_TITLE, title);
            instance.setArguments(args);
        }
        return instance;
    }

    public HistorialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historial, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCallback.onGetHistorial();
    }
}
