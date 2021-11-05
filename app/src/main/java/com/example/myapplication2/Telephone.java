package com.example.myapplication2;

public class Telephone {
    private String name;
    private int price;
    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {

        return "Наименование: " + getName() + ". Цена: " + getPrice() + "\nИмеется в наличии: " + (isChecked()?"Да":"Нет");
    }
}
