package com.example.myapplication2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static final String NME = "names";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new Fragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.default_fragment, fragment);
        ft.commit();

/*        if (savedInstanceState == null) {
            Collections.addAll(names, "Meizu", "Xiomi", "Nokia", "Sony", "IPhone");
        }*/

      /*  // получаем элемент ListView
        namesList = (ListView) findViewById(R.id.namesList);

        // создаем адаптер
        adapter = new ArrayAdapter(this,
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
        });*/
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putStringArrayList(NME, names);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        names = savedInstanceState.getStringArrayList(NME);
//        for(int i = 0; i < names.size(); i++) {
//            adapter.add(names.get(i));
//        }
//    }
//
//    public void selectAllElem(View view){
//        for(int i = 0; i < names.size(); i++) {
//            namesList.setItemChecked(i, true);
//        }
//    }
//
//    public void resetAllElem(View view){
//        for(int i = 0; i < namesList.getCount(); i++) {
//            namesList.setItemChecked(i, false);
//        }
//    }
//
//    public void outputSelectElem(View view){
//        ArrayList<String> str = new ArrayList<String>();
//        for(int i = 0; i < namesList.getCount(); i++) {
//            if (namesList.isItemChecked(i)) {
//                str.add(namesList.getItemAtPosition(i).toString());
//            }
//        }
//        Toast.makeText(this, str.toString(), Toast.LENGTH_LONG).show();
//    }
//
//    public void add(View view) {
//        EditText userName = (EditText) findViewById(R.id.userName);
//        String user = userName.getText().toString();
//
//        if (selectedNames.size() == 1) {
//            for(int i=0; i< selectedNames.size();i++){
//                adapter.remove(selectedNames.get(i));
//
//            }
//            namesList.clearChoices();
//
//            // очищаем массив выбраных объектов
//            selectedNames.clear();
//
//            adapter.notifyDataSetChanged();
//
//        }
//        if (!user.isEmpty()) {
//            adapter.add(user);
//            userName.setText("");
//            adapter.notifyDataSetChanged();
//        }
//
//    }
//
//    public void remove(View view){
//        // получаем и удаляем выделенные элементы
//        for(int i=0; i< selectedNames.size();i++){
//            adapter.remove(selectedNames.get(i));
//        }
//        // снимаем все ранее установленные отметки
//        namesList.clearChoices();
//
//        // очищаем массив выбраных объектов
//        selectedNames.clear();
//
//        adapter.notifyDataSetChanged();
//    }
//
//    /*public void search(View view) {
//        Intent intent = new Intent(this, SearchingResultActivity.class);
//*//*        intent.putExtra("names", names);*//*
//        EditText searchObj = (EditText) findViewById(R.id.searchObj);
//        String search = searchObj.getText().toString();
//        String result = "";
//        if (!search.isEmpty()) {
//            for (int i = 0; i < names.size(); i++) {
//                if (names.get(i).contains(search)) {
//                    result += names.get(i);
//                    result += ",";
//                }
//            }
//        }
//        intent.putExtra("names", result);
//        startActivity(intent);
//    }*/

}