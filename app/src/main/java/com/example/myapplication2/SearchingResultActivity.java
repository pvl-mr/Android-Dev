package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchingResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_result);
        //String names = intent.getStringExtra("message");
        Bundle arg = getIntent().getExtras();
        TextView namesList = (TextView) findViewById(R.id.namesList);
        String res = arg.get("names").toString();
        String result = "";
        for (Object name : res.split(",")) {
            result += name + "\n";
        }
        namesList.setText(result);

    }
}