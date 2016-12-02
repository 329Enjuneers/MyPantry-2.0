package com.example.z.mypantry20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button getStartedButton;
    Button continueButton;
    PantryDbHelper dbHelper = new PantryDbHelper(this);
    boolean enableContinueButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(dbHelper != null){
            // Database exist skip the welcome screen.
            enableContinueButton = false;
        }
        getStartedButton = (Button) findViewById(R.id.getStartedButton);
        continueButton   = (Button) findViewById(R.id.continueButton);
        setOnClickListeners();
    }

    public void setOnClickListeners()
    {
        enableContinueButton = true;
        if(enableContinueButton)
        {
            continueButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(MainActivity.this, CategoryOverviewView.class);
                    startActivity(i);
                }
            });
        } else {
            Intent i = new Intent(MainActivity.this, CategoryOverviewView.class);
            startActivity(i);
        }

        getStartedButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO Launch the Activity to create new pantry
                //Intent i = new Intent();
                //StartActivity(i);

                //testing
                Context context = getApplicationContext();
                CharSequence text = "Clicked get started";
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
}