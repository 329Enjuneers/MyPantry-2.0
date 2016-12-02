package com.example.z.mypantry20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PantryItemView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_item_view);


        Intent i = getIntent();
        Bundle extras = i.getExtras();
        PantryItem pantryItem = (PantryItem) extras.getSerializable("pantryItem");
        System.out.println("in pantry item view pantry item is " + pantryItem);
        setTitle(pantryItem.getName());


        TextView amountRemaining = (TextView) findViewById(R.id.amountRemaining);
        amountRemaining.setText(pantryItem.getAmountRemaining() + " " + pantryItem.getAmountRemainingUnit());

    }

    //TODO what else will be here? Photo? "CONSUME" button? etc

}
