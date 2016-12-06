package com.example.z.mypantry20;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class RecipeView extends AppCompatActivity
{

    FloatingActionButton addRecipeButton;
    Button submitRecipeButton;
    ArrayList<PantryItem> recipeItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        Intent i = getIntent();
        if (i != null) {
            ListView recipeList = (ListView) findViewById(R.id.recipeList);
            String name = i.getStringExtra("name");
            String amount = i.getStringExtra("amount");
            // TODO add element to view
            //TODO is this list populated with pantry items? We need to get pantry item here to add directly
            //PantryItem pantryItem = (PantryItem) i.getSerializable("pantryItem");


            //TODO we don't have unit here to do it this way...
            recipeItems.add(new PantryItem(name, Float.parseFloat(amount), "lb"));
            ListView listView = (ListView) findViewById(R.id.recipeList);
            //TODO how to show amount with name in the list: recipe_item_text?
            ArrayAdapter<PantryItem> adapter = new ArrayAdapter<>(RecipeView.this, R.layout.recipe_item_text, recipeItems);
            listView.setAdapter(adapter);


            System.out.println("Hey, I got an intent!");
        }

        addRecipeButton = (FloatingActionButton) findViewById(R.id.addRecipeButton);
        submitRecipeButton = (Button) findViewById(R.id.submitRecipeButton);
        setOnClickListeners();


    }

    private void setOnClickListeners() {
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecipeView.this, AddRecipeActivity.class);
                startActivity(i);
            }
        });

        submitRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update database with new values
                Intent i = new Intent(RecipeView.this, CategoryOverviewView.class);
                startActivity(i);
            }
        });
    }
}