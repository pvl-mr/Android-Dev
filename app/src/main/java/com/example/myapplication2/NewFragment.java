package com.example.myapplication2;
import static com.example.myapplication2.default_fragment.names;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;


public class NewFragment extends Fragment {
    String textItem;
    TextView textBoxName;
    TextView textBoxPrice;
    CheckBox checkBox;
    Button btnSave;
    Integer index;
    private SharedPreferences pref;
    private final String save_key = "save_key";
    Telephone tel;

    public NewFragment() {
        // Required empty public constructor
    }

    public NewFragment(String text, int index) {
        this.index = index;
        textItem = text;
    }

    public NewFragment(Telephone telephone, int index) {
        tel = telephone;
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new, container, false);
        textBoxName = v.findViewById(R.id.textBoxName);
        textBoxPrice = v.findViewById(R.id.textBoxPrice);
        checkBox = v.findViewById(R.id.cbSelected);
        btnSave = v.findViewById(R.id.add);


        if (tel != null) {
            Log.d("Telephone", tel.getName());
            Log.d("Telephone", tel.getPrice()+"");
            Log.d("Telephone", tel.isChecked()+"");
            textBoxName.setText(tel.getName());
            textBoxPrice.setText(tel.getPrice()+"");
            checkBox.setChecked(tel.isChecked());
        }

        getListeners();
        init();
        // Inflate the layout for this fragment
        return v;
        }

        private void getListeners() {
            btnSave.setOnClickListener((view) -> {
                Log.d("Index", (index==null)?"null":index.toString());
                if (index == null) {
                    names.add(getObj());
                } else {
                    names.set(index, getObj());
                }

                default_fragment.adapter.notifyDataSetChanged();
                textBoxName.setText("");
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            });

        }

        private Telephone getObj() {
            Telephone telephone = new Telephone();
            System.out.println(textBoxPrice.getText().toString().trim());
            try
            {
                String name = textBoxName.getText().toString().trim();
                int price = Integer.parseInt(textBoxPrice.getText().toString().trim());
                boolean isSelected = checkBox.isChecked();
                Log.d("Telephone!!!", name);
                Log.d("Telephone!!!", price + "");
                Log.d("Telephone!!!", isSelected + "");
                telephone.setName(name);
                telephone.setPrice(price);
                telephone.setChecked(isSelected);
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("NumberFormatException: " + nfe.getMessage());
            } catch (Exception e){
                System.out.println("Exception: " + e.getMessage());
            }
            return telephone;
        }

        private void init() {
            pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            Gson gson = new Gson();
            String myData = gson.toJson(getObj());
            editor.putString(save_key, myData);
            editor.commit();
        }

}