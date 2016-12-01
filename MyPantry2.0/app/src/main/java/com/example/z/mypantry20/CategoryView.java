package com.example.z.mypantry20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


        Intent i = getIntent();
        Bundle extras = i.getExtras();
        Category category = (Category) extras.getSerializable("category");
        setTitle(category.getName());

        TextView description = (TextView) findViewById(R.id.categoryDescription);
        ListView lv = (ListView) findViewById(R.id.pantryItemListView);

        //testing
        pantryItems.add(new PantryItem("milk", 10, "oz"));

        ArrayAdapter<PantryItem> arrayAdapter = new ArrayAdapter<PantryItem>(this, android.R.layout.simple_list_item_1, pantryItems);

        lv.setAdapter(arrayAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO send PantryItem information to PantryItemView for details about item: (ex photo, amount remiaing, consume)
//                Intent in = new Intent(CategoryView.this, PantryItemView.class);
//                //passing category object to CategoryView activity
//                in.putExtra("pantryItem", (PantryItem) adapterView.getItemAtPosition(i));
//                startActivity(in);
            }
        });

    }
}