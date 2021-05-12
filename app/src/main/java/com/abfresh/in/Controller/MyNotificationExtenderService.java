package com.abfresh.in.Controller;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.abfresh.in.NewProductDiscription;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;
import com.abfresh.in.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

public class MyNotificationExtenderService extends NotificationExtenderService {


    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
        OverrideSettings overrideSettings = new OverrideSettings();

        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                Toast.makeText(MyNotificationExtenderService.this, notification.payload.body, Toast.LENGTH_SHORT).show();

                Log.w("NTAG","mne Noti Body====>"+notification.payload.additionalData);
                // Sets the background notification color to Green on Android 5.0+ devices.
                Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.noti_sound);
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.new_logo_yellow);
                builder.setLargeIcon(icon);
                builder.setPriority(2);
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();

//                builder.setSound(soundUri);

                return builder
                        .setColor(new BigInteger("e47e1c", 16).intValue())
                        .setSmallIcon(R.drawable.new_logo_yellow)
                        .setBadgeIconType(R.drawable.new_logo_yellow)
                        .setSound(soundUri);





            }
        };
        notification.payload.sound = null;

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

        return true;
    }

//    @Override
//    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
//        OverrideSettings overrideSettings = new OverrideSettings();
//        overrideSettings.extender = new NotificationCompat.Extender() {
//            @Override
//            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
//                // Sets the background notification color to Red on Android 5.0+ devices.
////                RemoteViews mContentView = new RemoteViews(getPackageName(), R.layout.notification_one_signal_bg);
////                mContentView.setImageViewResource(R.id.nos_iv, R.drawable.new_logo_yellow);
//////                mContentView.setTextViewText(R.id.time_os_tv, receivedResult.payload.notificationID);
////                String title=receivedResult.payload.title!=null?receivedResult.payload.title:"";
////                mContentView.setTextViewText(R.id.head_os_tv,title);
////                mContentView.setTextViewText(R.id.dis_os_tv,  receivedResult.payload.body);
////
////
////                try {
////                    URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
////                    Bitmap bmp= BitmapFactory.decodeStream(url.openConnection().getInputStream());
////                    mContentView.setImageViewBitmap(R.id.ivbanner, bmp);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////                builder.setContent(mContentView);
//
//                Bitmap icon = BitmapFactory.decodeResource(getResources(),
//                        R.drawable.new_logo_yellow);
//                builder.setLargeIcon(icon);
//
//                return builder.setSmallIcon(R.drawable.new_logo_yellow).setShowWhen(true).setStyle(new NotificationCompat.BigTextStyle().bigText("message").setSummaryText("www.Abfresh.in"));
////                return builder.setColor(new BigInteger("FF0000FF", 16).intValue());
////                            .setSmallIcon(R.drawable.new_logo_yellow)
////                            .setContentTitle("title")
////                            .setContentText("message")
////                            .setLargeIcon(icon)
////                            .setStyle(new NotificationCompat.BigTextStyle().bigText("title"))
////                            .setStyle(new NotificationCompat.BigTextStyle().bigText("message").setSummaryText("#hashtag"))
////                            .setShowWhen(true)
////                            .setAutoCancel(true);
//            }
//        };
//
//        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
//        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);
//
//        return true;
//    }
}
