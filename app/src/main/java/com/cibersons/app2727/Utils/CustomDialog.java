package com.cibersons.app2727.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.cibersons.app2727.R;

/**
 * Created by Manuel on 9/3/2015.
 */
public class CustomDialog {

        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog);


            LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Button dialogButton = (Button) dialog.findViewById(R.id.ll);
                    // if button is clicked, close the custom dialog
//                    dialogButton.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.dismiss();
                }
            });

            dialog.show();

        }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(CustomDialog dialog);
        public void onDialogNegativeClick(CustomDialog dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;


}
