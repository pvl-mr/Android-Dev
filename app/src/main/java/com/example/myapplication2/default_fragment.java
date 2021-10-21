package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class default_fragment extends Fragment {

    public static final ArrayList<String> names = new ArrayList<>();
    ArrayList<String> selectedNames = new ArrayList<>();
    public static ArrayAdapter<String> adapter;
    ListView namesList;
    Button buttonAllElem;
    Button buttonSearch;
    Button buttonAdd;
    Button buttonReset;
    Button buttonDelete;
    Button buttonElemInToast;
    Button buttonEdit;
    EditText searchObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_default_fragment, container, false);

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
                String name = adapter.getItem(position);
                if (namesList.isItemChecked(position)) {
                    selectedNames.add(name);
                } else {
                    selectedNames.remove(name);
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
                if (names.get(i).toLowerCase().contains(searchString)) {
                    searchResultString.add(names.get(i));
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
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            NewFragment editFragment = new NewFragment(selectedNames.get(0), names.indexOf(selectedNames.get(0)));
            fragmentTransaction.replace(R.id.default_fragment, editFragment, "tag");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        return view;
    }

    public void selectAllElem(){
        for(int i = 0; i < names.size(); i++) {
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
        for(int i=0; i< selectedNames.size();i++){
            adapter.remove(selectedNames.get(i));
        }
        namesList.clearChoices();
        selectedNames.clear();
        adapter.notifyDataSetChanged();
    }
}