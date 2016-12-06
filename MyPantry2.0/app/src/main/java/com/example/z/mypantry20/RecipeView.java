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
                // update database with new values
                Intent i = new Intent(RecipeView.this, CategoryOverviewView.class);
                startActivity(i);
            }
        });
    }
}