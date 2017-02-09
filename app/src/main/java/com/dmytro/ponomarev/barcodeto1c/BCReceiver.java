package com.dmytro.ponomarev.barcodeto1c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BCReceiver extends BroadcastReceiver {

    private String barcodeStr;

    @Override
    public void onReceive(Context context, Intent intent) {
        byte[] barcode = intent.getByteArrayExtra("barocode");
        int lng = intent.getIntExtra("length", 0);
        this.barcodeStr = new String(barcode, 0, lng);
        if (lng > 0) {
            Intent intentFor1C = new Intent("com.google.android.c2dm.intent.RECEIVE");
            intentFor1C.putExtra("text", BCService.EventID);
            intentFor1C.putExtra("title", "1C");
            intentFor1C.putExtra("data", this.barcodeStr);
            if (BCService.BaseName != null || BCService.BaseName != "")  {
                intentFor1C.putExtra("base", BCService.BaseName);
            }
            context.sendBroadcast(intentFor1C);
        }
    }
}
