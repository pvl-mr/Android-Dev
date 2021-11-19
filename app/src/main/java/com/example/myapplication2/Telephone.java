package com.example.myapplication2;

public class Telephone {
    private String name;
    private int price;
    private boolean isAvailable;

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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {

        return "Наименование: " + getName() + ". Цена: " + getPrice() + "\nИмеется в наличии: " + (isAvailable()?"Да":"Нет");
    }
}
