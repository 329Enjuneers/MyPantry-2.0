package com.example.z.mypantry20;

import java.util.ArrayList;
import java.util.List;


public class Category {
    private List<PantryItem> pantryItemList;
    private String name;
    private String description;
    private int numberOfPantryItems;

    Category(List<PantryItem> pantryItemList, String name, String description) {
        this.pantryItemList = pantryItemList;
        this.name = name;
        this.description = description;
        numberOfPantryItems = pantryItemList.size();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPantryItems() {
        return numberOfPantryItems;
    }

    public List<PantryItem> getPantryItemList()
    {
        return pantryItemList;
    }

    @Override
    public String toString()
    {
        return "[Name: "  + name + ", Description: " + description + ", PantryItemList: " + pantryItemList.toString() + "]";
    }




}