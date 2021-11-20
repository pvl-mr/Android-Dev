package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class default_fragment extends Fragment {

    public static ArrayList<Telephone> names = new ArrayList<>();
    ArrayList<Telephone> selectedNames = new ArrayList<>();
    public static ArrayAdapter<Telephone> adapter;
    ListView namesList;
    Button buttonAllElem;
    Button buttonSearch;
    Button buttonAdd;
    Button buttonReset;
    Button buttonDelete;
    Button buttonElemInToast;
    Button buttonEdit;
    Button buttonSaveAll;
    Button buttonGetAll;
    Button buttonImport;
    EditText searchObj;
    private SharedPreferences pref;
    private final String save_key = "save_key";
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_default_fragment, container, false);

        // получаем элемент ListView
        namesList = view.findViewById(R.id.namesList);

        // создаем адаптер
        adapter = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_list_item_multiple_choice, names);

        // устанавливаем для списка адаптер
        namesList.setAdapter(adapter);

        //обработка установки и снятия отметки в списке
        namesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //получаем нажатый элемент
                Telephone tel = adapter.getItem(position);
                if (namesList.isItemChecked(position)) {
                    selectedNames.add(tel);
                } else {
                    selectedNames.remove(tel);
                }

            }
        });

        buttonAllElem = view.findViewById(R.id.buttonAllElem);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        searchObj = view.findViewById(R.id.searchObj);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonReset = view.findViewById(R.id.buttonReset);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonElemInToast = view.findViewById(R.id.buttonElemInToast);
        buttonEdit = view.findViewById(R.id.buttonEdit);
        buttonSaveAll = view.findViewById(R.id.buttonSaveAll);
        buttonGetAll = view.findViewById(R.id.buttonGetData);
        buttonImport = view.findViewById(R.id.buttonImport);

        buttonAllElem.setOnClickListener(v -> {
            selectAllElem();
        });

        buttonSearch.setOnClickListener(v -> {

            String searchString = searchObj.getText().toString().toLowerCase().trim();
            System.out.println("searchObj " + searchString);
            if (searchString.equals("")) {
                Toast.makeText(v.getContext(), "Заполните поле поиска", Toast.LENGTH_LONG).show();
                return;
            }
            ArrayList<String> searchResultString = new ArrayList<>();

            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).getName().toLowerCase().contains(searchString)) {
                    searchResultString.add(names.get(i).getName());
                    System.out.println("NAMES " + names.get(i));
                }
            }
            if (searchResultString.size() == 0) {
                Toast.makeText(v.getContext(), "Результатов не найдено", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(getActivity(), SearchingResultActivity.class);
            intent.putExtra("names", searchResultString);
            startActivity(intent);
        });
        buttonAllElem.setOnClickListener(v -> {
            selectAllElem();
        });
        buttonAdd.setOnClickListener(v -> {
            add();
        });
        buttonReset.setOnClickListener(v -> {
            resetAllElem();
        });
        buttonDelete.setOnClickListener(v -> {
            remove();
        });
        buttonElemInToast.setOnClickListener(v -> {
            outputSelectElem(v);
        });
        buttonElemInToast.setOnClickListener(v -> {
            outputSelectElem(v);
        });
        buttonEdit.setOnClickListener(v -> {
            if (selectedNames.size() > 1) {
                Toast.makeText(view.getContext(), "Выбранно больше одного элемента!", Toast.LENGTH_LONG).show();
                return;
            }
            if (selectedNames.size() < 1) {
                Toast.makeText(view.getContext(), "Элемент не выбран!", Toast.LENGTH_LONG).show();
                return;
            }
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Log.d("selected-names", names.indexOf(selectedNames.get(0))+"");
            NewFragment editFragment = new NewFragment(selectedNames.get(0), names.indexOf(selectedNames.get(0)));
            fragmentTransaction.replace(R.id.default_fragment, editFragment, "tag");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            namesList.setItemChecked(names.indexOf(selectedNames.get(0)), false);
            selectedNames.clear();
        });
        buttonSaveAll.setOnClickListener(v -> {
            saveAll();
        });

        buttonSaveAll.setOnClickListener(v -> {
            saveAll();
        });

        buttonGetAll.setOnClickListener(v -> {
            getAll();
        });

        buttonImport.setOnClickListener(v -> {
            importData();
        });
        return view;
    }

    public void selectAllElem(){
        for(int i = 0; i < names.size(); i++) {
            selectedNames.add(names.get(i));
            namesList.setItemChecked(i, true);
        }
    }

    public void resetAllElem(){
        for(int i = 0; i < namesList.getCount(); i++) {
            namesList.setItemChecked(i, false);
        }
    }

    public void outputSelectElem(View view){
        ArrayList<String> str = new ArrayList<String>();
        for(int i = 0; i < namesList.getCount(); i++) {
            if (namesList.isItemChecked(i)) {
                str.add(namesList.getItemAtPosition(i).toString());
            }
        }
        Toast.makeText(view.getContext(), str.toString(), Toast.LENGTH_LONG).show();
    }

    public void add() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewFragment addFragment = new NewFragment();
        fragmentTransaction.replace(R.id.default_fragment, addFragment, "tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
            }

    public void remove(){
        Log.d("size ---", selectedNames.size()+"");
        for(int i=0; i < selectedNames.size();i++){
            adapter.remove(selectedNames.get(i));
            Log.d("selected obj ---", selectedNames.get(i).toString());
        }
        namesList.clearChoices();
        selectedNames.clear();
        adapter.notifyDataSetChanged();
    }

    private void saveAll() {
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        editor.putInt("save_key_count", names.size());
        for (int i = 0; i < names.size(); i++) {
            String myData = gson.toJson(names.get(i));
            editor.putString(save_key + i + "", myData);
        }
        editor.apply();
        Toast.makeText(view.getContext(), "Сохранено!", Toast.LENGTH_LONG).show();
    }

    private void getAll() {
        if (pref == null)
            return;
        Gson gson = new Gson();
        ArrayList<Telephone> telList= new ArrayList<>();
        Log.d("save-key ", save_key);
        Integer count = pref.getInt("save_key_count", 0);
        for (int i = 0; i < count; i++) {
            String data = pref.getString( save_key + i + "", "empty");
            telList.add(gson.fromJson(data, Telephone.class));
        }
        adapter = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_list_item_multiple_choice, telList);
        namesList.setAdapter(adapter);
        names = telList;
        adapter.notifyDataSetChanged();
        Toast.makeText(view.getContext(), "Получено!", Toast.LENGTH_LONG).show();
        Log.d("telephone size ", telList.size()+"");
        for (Telephone telephone: telList) {
            Log.d("telephone ", telephone.toString());
        }
        Log.d("telephone ", "--------------------");
    }

    private void importData() {
        String jsonString;
        try (InputStream is = getResources().getAssets().open("data.json")){
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);

            jsonString = new String(buffer, StandardCharsets.UTF_8);
            Log.d("DATA!!!!", jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            Log.d("temp name", jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("telephones");
            Log.d("temp name", jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Telephone temp = new Telephone();
                Log.d("temp name", obj.getString("name"));
                Log.d("temp price", obj.getInt("price")+"");
                Log.d("temp boolean", obj.getBoolean("isSelected")+"");
                temp.setName(obj.getString("name"));
                temp.setPrice(obj.getInt("price"));
                temp.setChecked(obj.getBoolean("isSelected"));
                names.add(temp);
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(view.getContext(), "Импортировано!", Toast.LENGTH_LONG).show();
    }
}