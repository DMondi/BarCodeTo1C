package com.dmytro.ponomarev.barcodeto1c;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

public class BCService extends Service {
    private static String SCAN_MESSAGE;
    public static String EventID;
    public static String BaseName;
    public static boolean isStarted;

    public BCService() {
        isStarted = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences prefs;
        prefs = getSharedPreferences(MainActivity.APP_PREFS, Context.MODE_PRIVATE);

        if(prefs.contains("edtScanMessage")){
            SCAN_MESSAGE = prefs.getString("edtScanMessage","");
            EventID = prefs.getString("edtEventID","1");
            BaseName = prefs.getString("edtBaseName",null);
            registerReceiver(new BCReceiver(), new IntentFilter(SCAN_MESSAGE));
            isStarted = true;
            Toast.makeText(this, getString(R.string.bc_started), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Service NOT Started!", Toast.LENGTH_SHORT).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, R.string.svcStoped, Toast.LENGTH_SHORT).show();
        isStarted = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
