package com.example.z.mypantry20;

import java.util.List;


public class Category
{
    List<PantryItem> pantryItemList;
    String name;
    String description;
    int numberOfPantryItems;

    Category(List<PantryItem> pantryItemList, String name, String description)
    {
        this.pantryItemList = pantryItemList;
        this.name           = name;
        this.description    = description;
        numberOfPantryItems = pantryItemList.size();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumberOfPantryItems()
    {
        return numberOfPantryItems;
    }




}