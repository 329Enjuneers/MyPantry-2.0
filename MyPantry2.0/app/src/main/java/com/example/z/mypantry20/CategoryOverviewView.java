package com.example.z.mypantry20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoryOverviewView extends AppCompatActivity {
    FloatingActionButton addCategoryButton;
    Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview_view);
        addCategoryButton = (FloatingActionButton) findViewById(R.id.addCategoryButton);
        addRecipeButton = (Button) findViewById(R.id.addRecipeButton);
        setOnClickListeners();
    }


    @Override
    protected void onResume() {
        super.onResume();
        new FetchCategoryTask().execute();
    }

    public void setOnClickListeners() {
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryOverviewView.this, AddCategoryActivity.class);
                startActivity(i);
            }
        });
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryOverviewView.this, RecipeView.class);
                startActivity(i);
            }
        });
    }

    private class FetchCategoryTask extends AsyncTask<Void, Void, ArrayList<Category>> {
        private ProgressDialog progressDialog = new ProgressDialog(CategoryOverviewView.this);
        ArrayAdapter<Category> adapter;
        FetchCategoryTask(){
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        protected ArrayList<Category> doInBackground(Void... s) {
            ArrayList<Category> categories = new ArrayList<>();
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Category.NAME + ", " + PantryContract.Category.DESCRIPTION + " FROM " + PantryContract.Category.TABLE_NAME,null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    categories.add(new Category(new ArrayList<PantryItem>(), c.getString(c.getColumnIndex(PantryContract.Category.NAME)), c.getString
                            (c.getColumnIndex(PantryContract.Category.DESCRIPTION))));
                }while(c.moveToNext());
            }
            c.close();
            db.close();
            adapter = new ArrayAdapter<>(CategoryOverviewView.this, R.layout.category_item_text, categories);
            return categories;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<Category> result) {
            ListView listView = (ListView) findViewById(R.id.categoryList);
            progressDialog.dismiss();
            if(result.size() > 0){
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                (findViewById(R.id.no_category_error)).setVisibility(View.INVISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(CategoryOverviewView.this, CategoryView.class);
                        //passing category object to CategoryView activity
                        in.putExtra("category", (Category) adapterView.getItemAtPosition(i));
                        in.putExtra("category_id",i);
                        startActivity(in);
                    }
                });
            }else{
                listView.setVisibility(View.INVISIBLE);
                String error = "No Categories.";
                ((TextView) findViewById(R.id.no_category_error)).setText(error);
                (findViewById(R.id.no_category_error)).setVisibility(View.VISIBLE);
            }
        }
    }
}
