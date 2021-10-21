package com.example.myapplication2;
import static com.example.myapplication2.default_fragment.names;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;


public class NewFragment extends Fragment {
    String textItem;
    TextView textBox;
    Button btnSave;
    Integer index;

    public NewFragment() {
        // Required empty public constructor
    }

    public NewFragment(String text, int index) {
        this.index = index;
        textItem = text;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new, container, false);
        textBox = v.findViewById(R.id.userName);
        btnSave = v.findViewById(R.id.add);
        if (textItem != null) textBox.setText(textItem);

        btnSave.setOnClickListener((view) -> {
            textItem = textBox.getText().toString().trim();
            if (textItem.isEmpty()) return;

            if (index == null) {
                names.add(textItem);
            } else {
                names.set(index, textItem);
            }

            default_fragment.adapter.notifyDataSetChanged();
            textBox.setText("");
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        });
        // Inflate the layout for this fragment
        return v;
        }
}