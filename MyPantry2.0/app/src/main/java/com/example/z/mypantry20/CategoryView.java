package com.example.z.mypantry20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryView extends AppCompatActivity
{

    private ListView lv;
    private ArrayList<PantryItem> pantryItems = new ArrayList<PantryItem>(); ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        setTitle("MyPantry 2.0 - Category");


        TextView name = (TextView) findViewById(R.id.categoryName);
        TextView description = (TextView) findViewById(R.id.categoryDescription);
        ListView lv = (ListView) findViewById(R.id.pantryItemListView);

        //testing
        pantryItems.add(new PantryItem("milk", 10, "oz"));

        ArrayAdapter<PantryItem> arrayAdapter = new ArrayAdapter<PantryItem>(this, android.R.layout.simple_list_item_1, pantryItems);

        lv.setAdapter(arrayAdapter);

    }
}