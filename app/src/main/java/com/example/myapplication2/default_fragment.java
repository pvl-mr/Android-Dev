package com.example.myapplication2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.io.IOException;
import java.io.InputStream;

import com.example.myapplication2.database.DbManager;
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
    public static String type;
    private DbManager dbManager;
    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    MyService myService;
    final String LOG_TAG = "myLogs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_default_fragment, container, false);
        intent = new Intent(getActivity(), MyService.class);
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
        init();
        Bundle data = getActivity().getIntent().getExtras();
        if (data.containsKey("type")) {
            type = data.getString("type");
            Toast.makeText(view.getContext(), "type of storage is " + type, Toast.LENGTH_SHORT).show();
        }

        if (type.equalsIgnoreCase("database")){
            getActivity().startService(new Intent(getActivity(), MyService.class));
            buttonSaveAll.setVisibility(View.GONE);

        }
        namesList = view.findViewById(R.id.namesList);
        adapter = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_list_item_multiple_choice, names);
        namesList.setAdapter(adapter);
        namesList.setOnItemClickListener((adapterView, view, position, l) -> {
            Telephone tel = adapter.getItem(position);
            if (namesList.isItemChecked(position)) {
                selectedNames.add(tel);
            } else {
                selectedNames.remove(tel);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().bindService(intent, sConn, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!bound) return;
        getActivity().unbindService(sConn);
        bound = false;
    }

    public void init() {
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

        buttonAllElem.setOnClickListener(v -> selectAllElem());
        buttonSearch.setOnClickListener(v -> search(v));
        buttonAllElem.setOnClickListener(v -> selectAllElem());
        buttonAdd.setOnClickListener(v -> add());
        buttonReset.setOnClickListener(v -> resetAllElem());
        buttonDelete.setOnClickListener(v ->remove());
        buttonElemInToast.setOnClickListener(v -> outputSelectElem(v));
        buttonElemInToast.setOnClickListener(v -> outputSelectElem(v));
        buttonEdit.setOnClickListener(v -> edit());
        buttonSaveAll.setOnClickListener(v -> saveAll());
        buttonSaveAll.setOnClickListener(v -> saveAll());
        buttonGetAll.setOnClickListener(v -> getAll());
        buttonImport.setOnClickListener(v -> importData());
    }

    public void search(View v) {
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
    }

    public void edit(){
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
        NewFragment editFragment = new NewFragment(selectedNames.get(0), names.indexOf(selectedNames.get(0)));
        fragmentTransaction.replace(R.id.default_fragment, editFragment, "tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        namesList.setItemChecked(names.indexOf(selectedNames.get(0)), false);
        selectedNames.clear();
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
        NewFragment addFragment;
        if (type.equalsIgnoreCase("database")) {
            addFragment = new NewFragment(myService);
        } else {
            addFragment = new NewFragment();
        }
        fragmentTransaction.replace(R.id.default_fragment, addFragment, "tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void remove(){
        Log.d("size ---", selectedNames.size()+"");
        for(int i=0; i < selectedNames.size();i++){
            adapter.remove(selectedNames.get(i));
        }
        namesList.clearChoices();
        selectedNames.clear();
        adapter.notifyDataSetChanged();
    }

    private void saveAllPr(){
        new Thread(() -> {
            pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            Gson gson = new Gson();
            editor.putInt("save_key_count", names.size());
            for (int i = 0; i < names.size(); i++) {
                String myData = gson.toJson(names.get(i));
                editor.putString(save_key + i + "", myData);
            }
            editor.apply();
        }).start();
    }

    private void saveAll() {
        saveAllPr();
        Toast.makeText(view.getContext(), "Сохранено!", Toast.LENGTH_LONG).show();
    }

    public void getAllFromDb() {
        names = myService.getTelephones();
    }

    public void getAllFromPr() {
        if (pref == null)
            return;
        new Thread(() -> {
            Gson gson = new Gson();
            ArrayList<Telephone> telList= new ArrayList<>();
            Integer count = pref.getInt("save_key_count", 0);
            for (int i = 0; i < count; i++) {
                String data = pref.getString( save_key + i + "", "empty");
                telList.add(gson.fromJson(data, Telephone.class));
            }
            names = telList;
        }).start();
    }

    private void getAll() {
        if (type.equalsIgnoreCase("database")) {
            getAllFromDb();
        } else {
            getAllFromPr();
        }
        adapter = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_list_item_multiple_choice, names);
        namesList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(view.getContext(), "Получено!", Toast.LENGTH_LONG).show();
    }

    private void importData() {
        final Handler handler = new Handler();
        new Thread(() -> handler.post(() -> {
            String jsonString;
            try {
                InputStream is = view.getContext().getAssets().open("data.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonString = new String(buffer, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("telephones");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Telephone temp = new Telephone();
                    String name = obj.getString("name");
                    int price = obj.getInt("price");
                    boolean isAvailable = obj.getBoolean("isSelected");
                    temp.setName(name);
                    temp.setPrice(price);
                    temp.setAvailable(isAvailable);
                    if (type.equalsIgnoreCase("database")){
                        myService.insertTelephone(name, price, isAvailable);
                    }
                    names.add(temp);
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(view.getContext(), "Импортировано!", Toast.LENGTH_LONG).show();
        })).start();

    }
}