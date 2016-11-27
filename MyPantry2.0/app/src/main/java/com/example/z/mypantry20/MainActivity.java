package com.example.z.mypantry20;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button getStartedButton;
    Button continueButton;
    private PantryDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStartedButton = (Button) findViewById(R.id.getStartedButton);
        continueButton   = (Button) findViewById(R.id.continueButton);
        setOnClickListeners();
        dbHelper = new PantryDbHelper(getApplicationContext());
        Log.d("Successfully created : ",dbHelper.getDatabaseName());
        ContentValues v = new ContentValues();
        v.put(PantryContract.Pantry.ITEM_NAME, "Apple");
        v.put(PantryContract.Pantry.ITEM_CATEGORY, "Food");
        v.put(PantryContract.Pantry.AMOUNT_REMAINING, "1.7");
        v.put(PantryContract.Pantry.AMOUNT_REMAINING_UNIT, "lb");
        dbHelper.getWritableDatabase().insert(PantryContract.Pantry.TABLE_NAME, null, v);
    }

    public void setOnClickListeners()
    {
        //TODO change to check if database already exists
        boolean enableContinueButton = true;
        if(enableContinueButton)
        {
            continueButton.setEnabled(true);
            continueButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //TODO load database
                    //TODO Launch the user's current CategoryOverView
                    Intent i = new Intent(MainActivity.this, MyPantryView.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            continueButton.setEnabled(false);
        }

        getStartedButton.setEnabled(true);
        getStartedButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO Launch the Activity to create new pantry
                //Intent i = new Intent();
                //StartActivity(i);
            }
        });
    }
}