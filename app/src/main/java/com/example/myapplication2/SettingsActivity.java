package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication2.default_fragment.names;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import static com.example.myapplication2.default_fragment.pref_name;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    CheckBox cbDatabase;
    CheckBox cbPreference;
    ServiceConnection sConn;
    Intent intent;
    public static MyService myService;
    final String LOG_TAG = "myLogs";
    boolean bound = false;
    private final String save_key = "save_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cbDatabase = findViewById(R.id.cbDatabase);
        cbPreference = findViewById(R.id.cbPreference);
        intent = new Intent(SettingsActivity.this, MyService.class);
        sConn = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
                myService = ((MyService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
                bound = false;
            }
        };
        SettingsActivity.this.startService(new Intent(SettingsActivity.this, MyService.class));
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sync();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        SettingsActivity.this.bindService(intent, sConn, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!bound) return;
        SettingsActivity.this.unbindService(sConn);
        bound = false;
    }

    public void moveNext(View view) {
        String type = "";
        if (names != null)
            names.clear();
        if (cbDatabase.isChecked() && !cbPreference.isChecked()) {
            type = "Database";
        } else if (cbPreference.isChecked() && !cbDatabase.isChecked()) {
            type = "Preference";
        } else {
            Toast.makeText(this, "Необходимо выбрать 1 тип хранения", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void sync(){
        ArrayList<Telephone> dbTelephoneList = myService.getTelephones();
        ArrayList<Telephone> prefTelephoneList;
        SharedPreferences saved_pref = SettingsActivity.this.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        Integer count = saved_pref.getInt("save_key_count", 0);
        if (saved_pref != null) {
            prefTelephoneList = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < count; i++) {
                String data = saved_pref.getString( save_key + i + "", "empty");
                prefTelephoneList.add(gson.fromJson(data, Telephone.class));
            }
            if (myService.getTelephones().size() == 0) {
                for (int i = 0; i < count; i++) {
                    myService.insertTelephone(prefTelephoneList.get(i));
                }
                Toast.makeText(this, myService.getTelephones().size()+"", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("count ", count+"");
            for (Telephone tl: dbTelephoneList) {
                Log.d("777dbtl", tl.toString());
            }

            for (Telephone tl: prefTelephoneList) {
                Log.d("777prtl", tl.toString());
            }
            for (int i = 0; i < count; i++) {
                    if (!dbTelephoneList.contains(prefTelephoneList.get(i))) {
                        myService.insertTelephone(prefTelephoneList.get(i));
                        Log.d("777inserttel" + i, prefTelephoneList.get(i).toString());
                    }
                }
            }
        Toast.makeText(this, myService.getTelephones().size()+"", Toast.LENGTH_SHORT).show();
        }


    public String getString(boolean temp) {
        return (temp == true) ? "true" : "false";
    }
}