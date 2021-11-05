package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Telephone> {
    private int listViewId;
    private ArrayList<Telephone> telephoneList;
    private Context context;

    public ListAdapter(Context context, int listViewId, ArrayList<Telephone> telephoneList) {
        super(context, listViewId, telephoneList);
        this.listViewId = listViewId;
        this.telephoneList = telephoneList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(listViewId, parent, false);
        TextView name = itemView.findViewById(R.id.textBoxName);
        TextView price = itemView.findViewById(R.id.tvPrice);
        CheckBox isAvailable = itemView.findViewById(R.id.cbSelected);

        name.setText(telephoneList.get(position).getName());
        price.setText(telephoneList.get(position).getPrice());
        isAvailable.setChecked(telephoneList.get(position).isChecked());
        return itemView;
    }
}
