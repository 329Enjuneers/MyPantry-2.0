package com.example.z.mypantry20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by josephmcgovern on 12/6/16.
 */

public class AddRecipeActivity extends AppCompatActivity {

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        addButton = (Button) findViewById(R.id.addRecipeButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddRecipeActivity.this, RecipeView.class);
                Spinner spinner = (Spinner) findViewById(R.id.pantry_item_spinner);
                String name = spinner.getSelectedItem().toString();
                EditText text = (EditText) findViewById(R.id.amount);
                String amount = text.getText().toString();
                i.putExtra("name", name);
                i.putExtra("amount", amount);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchPantryItemNames().execute();
    }

    private class FetchPantryItemNames extends AsyncTask<Void, Void, ArrayList<String>> {
        private ProgressDialog progressDialog = new ProgressDialog(AddRecipeActivity.this);
        ArrayAdapter<String> adapter;
        FetchPantryItemNames(){
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        protected ArrayList<String> doInBackground(Void... s) {
            ArrayList<String> pantryItemNames = new ArrayList<>();
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Pantry.ITEM_NAME + " FROM " + PantryContract.Pantry.TABLE_NAME,null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    pantryItemNames.add(c.getString(0));
                }while(c.moveToNext());
            }
            c.close();
            db.close();
            adapter = new ArrayAdapter<>(AddRecipeActivity.this, android.R.layout.simple_spinner_dropdown_item, pantryItemNames);
            return pantryItemNames;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<String> result) {
            progressDialog.dismiss();
            Spinner spinner = (Spinner) findViewById(R.id.pantry_item_spinner);
            if(result.size() > 0){
                spinner.setAdapter(adapter);
                spinner.setVisibility(View.VISIBLE);
            }
        }
    }
}
