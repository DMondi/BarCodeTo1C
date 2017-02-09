package com.dmytro.ponomarev.barcodeto1c;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{

    public static final String APP_PREFS = "bc_settings";

    Button btnStart;
    Button btnStop;
    EditText edtScanMessage;
    EditText edtEventID;
    EditText edtBaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        edtScanMessage = (EditText)findViewById(R.id.edtScanMessage);
        edtEventID = (EditText)findViewById(R.id.edtEventID);
        edtBaseName = (EditText)findViewById(R.id.edtBaseName);

        btnStart.setEnabled(!BCService.isStarted);
        btnStop.setEnabled(BCService.isStarted);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        getSettings();

    }

    public void getSettings(){

        SharedPreferences prefs;
        prefs = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        if(prefs.contains("edtScanMessage")){
            edtScanMessage.setText(prefs.getString("edtScanMessage",""));

        }else {
            edtScanMessage.setText("scan.rcv.message");
        }

        if(prefs.contains("edtEventID")){
            edtEventID.setText(prefs.getString("edtEventID",""));
        }else {
            edtEventID.setText("1");
        }

        if(prefs.contains("edtBaseName")){
            edtBaseName.setText(prefs.getString("edtBaseName",""));
        }else {
            edtBaseName.setText("");
        }

    }

    public void setSettings(){

        SharedPreferences prefs;
        prefs = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("edtScanMessage",edtScanMessage.getText().toString());
        editor.apply();

        editor.putString("edtEventID",edtEventID.getText().toString());
        editor.apply();

        editor.putString("edtBaseName",edtBaseName.getText().toString());
        editor.apply();

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        switch (button.getId()){
            case R.id.btnStart:
                setSettings();
                startService(new Intent(this, BCService.class));
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                break;
            case R.id.btnStop:
                stopService(new Intent(this, BCService.class));
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                break;
            default:
        }
    }
}

