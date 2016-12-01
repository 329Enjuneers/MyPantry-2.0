package com.example.z.mypantry20;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.z.mypantry20.R.layout.pantry_item_text;

public class CategoryView extends AppCompatActivity
{

    private ListView lv;
    private ArrayList<PantryItem> pantryItems = new ArrayList<PantryItem>(); ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_category_view);
        setTitle("MyPantry 2.0 - Category");
        Log.d("Tag", "IS THIS WORKING");


        TextView name = (TextView) findViewById(R.id.categoryName);
        name.setText("Test Category");
        TextView description = (TextView) findViewById(R.id.categoryDescription);
        description.setText("Test Description");
        ListView lv = (ListView) findViewById(R.id.pantryItemListView);

        //testing
        pantryItems.add(new PantryItem("milk", 10, "oz"));
        ArrayList<String> names = new ArrayList<String>();
        names.add("milk");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, pantry_item_text, names);

        lv.setAdapter(arrayAdapter);

    }
}