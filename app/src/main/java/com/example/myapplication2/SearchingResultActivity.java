package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchingResultActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_result);
        Bundle data = getIntent().getExtras();
        listView = findViewById(R.id.searchListView);
        if (data.containsKey("names")) {
            Object results = data.get("names");
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, (ArrayList<String>) results);
            listView.setAdapter(adapter);
        }
    }
}