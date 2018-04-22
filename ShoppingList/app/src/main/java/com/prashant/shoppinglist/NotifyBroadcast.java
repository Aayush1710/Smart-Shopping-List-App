package com.prashant.shoppinglist;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prashant.shoppinglist.SmartListTab;




public class NotifyBroadcast extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";

    public static String NOTIFICATION = "You are in need of: ";
    SmartListTab slt=new SmartListTab();

    //final View rootView = inflater.inflate(R.layout.smart_list_fragment, container, false);
    public void onReceive(Context context, Intent intent) {
        slt.setDefaultTabWithGetList(context,intent);
    }
    public void sendNotification(Context context,Intent intent,String msg){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotifyBroadcast.NOTIFICATION+=msg;
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 1);
        notificationManager.notify(id, notification);

    }
}