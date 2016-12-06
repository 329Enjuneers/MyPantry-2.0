package com.example.z.mypantry20;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Button getStartedButton;
    Button continueButton;
    public static PantryDbHelper dbHelper;
    boolean enableContinueButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (dbHelper == null) {
            dbHelper = new PantryDbHelper(this);
        }
        else {
            enableContinueButton = false;
        }
        getStartedButton = (Button) findViewById(R.id.getStartedButton);
        continueButton   = (Button) findViewById(R.id.continueButton);
        setOnClickListeners();
        new DbSetupTask().execute();
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

    private class DbSetupTask extends AsyncTask<Void, Void, Void> {
        DbSetupTask(){
        }

        @Override
        protected void onPreExecute() {
        }

        protected Void doInBackground(Void... s) {
            // Initialize Database and tables.
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            return null;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(Void result) {
            // Nothing to do on Main thread
        }
    }
}