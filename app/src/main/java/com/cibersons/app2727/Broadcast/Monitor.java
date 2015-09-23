package com.cibersons.app2727.Broadcast;

/**
 * Created by Manuel on 8/25/2015.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cibersons.app2727.R;

public class Monitor extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();

//    public void onReceive(Context context, Intent intent) {
//
//        // Retrieves a map of extended data from the intent.
//        final Bundle bundle = intent.getExtras();
//
//        try {
//
//            if (bundle != null) {
//
//                final Object[] pdusObj = (Object[]) bundle.get("pdus");
//
//                for (int i = 0; i < pdusObj.length; i++) {
//
//                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                    if (currentMessage.getDisplayMessageBody().toUpperCase().contains("PRUEBA")) {
////                Log.i("SMS:" + msg.getOriginatingAddress(),
////                        msg.getMessageBody());
//////                showNotification(context);
////                abortBroadcast();
//                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//
//                        String senderNum = phoneNumber;
//                        String message = currentMessage.getDisplayMessageBody();
//
//                        Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
//
//
//                        // Show Alert
//                        int duration = Toast.LENGTH_LONG;
//                        Toast toast = Toast.makeText(context,
//                                "senderNum: "+ senderNum + ", message: " + message, duration);
//                        toast.show();
//            }
//
//
//                } // end for loop
//            } // bundle is null
//
//        } catch (Exception e) {
//            Log.e("SmsReceiver", "Exception smsReceiver" +e);
//
//        }
//    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] rawMsgs = (Object[]) intent.getExtras().get("pdus");
        for (Object raw : rawMsgs) {
            SmsMessage msg = SmsMessage.createFromPdu((byte[]) raw);
            if (msg.getMessageBody().toUpperCase().contains("PRUEBA")) {
                Log.i("SMS:" + msg.getOriginatingAddress(),
                        msg.getMessageBody());
                showNotification(context);
                abortBroadcast();
            }
        }
    }

    private void showNotification(Context context) {
        // prepare intent which is triggered if the
// notification is selected

        Intent intent = new Intent();
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(context)
                .setContentTitle("Mensaje Recibido " + "APP2727")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.ic_done)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
//                .addAction(R.drawable.ic_home_white_24dp, "Call", pIntent)
//                .addAction(R.drawable.ic_favorite_border_white_24dp, "More", pIntent)
//                .addAction(R.drawable.ic_keyboard_arrow_right_white_24dp, "And more", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }
}