package com.abfresh.in.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListner mListener;
    Boolean b;
    String abcd,xyz;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            // b=sender.endsWith("WNRCRP");  //Just to fetch otp sent from WNRCRP
            String messageBody = smsMessage.getMessageBody();
            abcd=messageBody.replaceAll("[^0-9]","");   // here abcd contains otp
//            which is in number format
            //Pass on the text to our listener.
            if(b) {
                mListener.messageReceived(abcd);  // attach value to interface object
            }
            else{
            }
        }
    }
    public static void bindListener(SmsListner listener) {
        mListener = listener;
    }
}
