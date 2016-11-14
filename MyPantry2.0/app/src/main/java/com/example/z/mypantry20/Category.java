package com.example.z.mypantry20;

import java.util.List;

/**
 * Created by Z on 10-Nov-16.
 */


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
}