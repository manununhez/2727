package cibersone.app2727.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cibersone.app2727.R;

/**
 * Created by Manuel on 8/19/2015.
 */
public class HistorialFragment extends Fragment {

    private static HistorialFragment instance;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
