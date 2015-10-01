package com.cibersons.app2727.fragment;


import android.support.v4.app.Fragment;

import com.cibersons.app2727.utils.Utils;

/**
 * Created by manunez on 01/10/2015.
 */
public class RootFragment extends Fragment {
    public void showDialogOk(final String title, final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.customAlertDialogWithOk(getActivity(), title, msg);
            }
        });
    }

    public void showDialogWithOptions(final String title, final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.customAlertDialogWithOptions(getActivity(), title, msg);
            }
        });
    }
}
