package com.example.z.mypantry20;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeView extends AppCompatActivity {

    FloatingActionButton addRecipeButton;
    Button submitRecipeButton;
    HashMap<Integer, Float> pantryItemIdToAmountMap;
    ArrayList<String> recipeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        setTitle("Recipe");

        addRecipeButton = (FloatingActionButton) findViewById(R.id.addRecipeButton);
        submitRecipeButton = (Button) findViewById(R.id.submitRecipeButton);
        setOnClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchRecipeItemNames().execute();
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
                removeAmountsFromPantryItems();
                Toast.makeText(RecipeView.this, "Successfully updated pantry items",
                        Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }

            private void removeAmountsFromPantryItems() {
                PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                for (int id : pantryItemIdToAmountMap.keySet()) {
                    Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Pantry.AMOUNT_REMAINING +  " FROM " + PantryContract.Pantry.TABLE_NAME + " WHERE " + PantryContract.Pantry._ID + " = " + id,null);
                    c.moveToFirst();
                    if (c.getCount() > 0) {
                        String amountString = c.getString(0);
                        float amount = new Float(amountString);
                        float amountToSubtract = pantryItemIdToAmountMap.get(id);
                        float newAmount = amount - amountToSubtract;
                        ContentValues cv = new ContentValues();
                        cv.put(PantryContract.Pantry.AMOUNT_REMAINING, Float.toString(newAmount));
                        db.update(PantryContract.Pantry.TABLE_NAME, cv, PantryContract.Pantry._ID + " = "+id, null);
                    }
                    c.close();
                }
                db.execSQL("delete from "+ PantryContract.Recipe.TABLE_NAME);
                db.close();

            }

        });
    }

    private class FetchRecipeItemNames extends AsyncTask<Void, Void, ArrayList<String>> {
        private ProgressDialog progressDialog = new ProgressDialog(RecipeView.this);
        ArrayAdapter<String> adapter;
        FetchRecipeItemNames(){
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        protected ArrayList<String> doInBackground(Void... s) {
            pantryItemIdToAmountMap = new HashMap<Integer, Float>();
            recipeNames = new ArrayList<String>();
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Recipe.PANTRY_ITEM_ID + ", " + PantryContract.Recipe.AMOUNT + " FROM " + PantryContract.Recipe.TABLE_NAME,null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    int id = c.getInt(0);
                    String amountString = c.getString(1);
                    float amount = new Float(amountString);
                    String name = getPantryItemName(id);
                    pantryItemIdToAmountMap.put(id, amount);
                    recipeNames.add(name + " - " + amount);
                }while(c.moveToNext());
            }
            c.close();
            db.close();
            adapter = new ArrayAdapter<>(RecipeView.this, android.R.layout.simple_spinner_dropdown_item, recipeNames);

            return recipeNames;
        }

        private String getPantryItemName(int id) {
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Pantry.ITEM_NAME + " FROM " + PantryContract.Pantry.TABLE_NAME + " WHERE _ID IS " + id, null);
            c.moveToFirst();
            String name = "";
            if (c.getCount() > 0) {
                name = c.getString(0);
            }
            c.close();
            db.close();
            return name;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<String> result) {
            progressDialog.dismiss();
            ListView listView = (ListView) findViewById(R.id.recipeList);
            if(result.size() > 0){
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
            }
            if (pantryItemIdToAmountMap.size() == 0) {
                TextView errText = (TextView) findViewById(R.id.no_recipe_error);
                errText.setText("Click the plus button below to add a new recipe item!");
            }
        }
    }
}
