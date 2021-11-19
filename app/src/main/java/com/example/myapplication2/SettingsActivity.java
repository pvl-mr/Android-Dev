package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication2.default_fragment.names;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    CheckBox cbDatabase;
    CheckBox cbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cbDatabase = findViewById(R.id.cbDatabase);
        cbPreference = findViewById(R.id.cbPreference);
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
}