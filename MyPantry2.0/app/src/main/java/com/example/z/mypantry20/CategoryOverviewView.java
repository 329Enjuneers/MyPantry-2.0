package com.example.z.mypantry20;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CategoryOverviewView extends AppCompatActivity {
    String title = "Categories";
    Button addCategoryButton;
    ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview_view);
        setTitle(title);
        new FetchCategoryTask().execute();

        addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
        setOnClickListeners();
        PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
        Log.d("Successfully created : ",dbHelper.getDatabaseName());
//        ContentValues v = new ContentValues();
//        v.put(PantryContract.Pantry.ITEM_NAME, "Apple");
//        v.put(PantryContract.Pantry.CATEGORY_ID, "1");
//        v.put(PantryContract.Pantry.AMOUNT_REMAINING, "1.7");
//        v.put(PantryContract.Pantry.AMOUNT_REMAINING_UNIT, "lb");
//        dbHelper.getWritableDatabase().insert(PantryContract.Pantry.TABLE_NAME, null, v);
//        ContentValues cv = new ContentValues();
//        cv.put(PantryContract.Category.NAME, "Food");
//        cv.put(PantryContract.Category.DESCRIPTION, "FOOD CATEGORY");
//        dbHelper.getWritableDatabase().insert(PantryContract.Category.TABLE_NAME, null, cv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchCategoryTask().execute();
    }

    public void setOnClickListeners() {
        addCategoryButton.setEnabled(true);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryOverviewView.this, AddCategoryActivity.class);
                startActivity(i);
            }
        });
    }

    private class FetchCategoryTask extends AsyncTask<Void, Void, ArrayList<String>> {
        private ProgressDialog progressDialog = new ProgressDialog(CategoryOverviewView.this);
        public FetchCategoryTask(){
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        protected ArrayList<String> doInBackground(Void... s) {
            ArrayList<String> categories = new ArrayList<String>();
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Category.NAME + " FROM " + PantryContract.Category.TABLE_NAME,
                    null);
            c.moveToFirst();
            if (c.getColumnCount() > 0) {
                do {
                    categories.add(c.getString(0));
                }while(c.moveToNext());
            }
            c.close();
            return categories;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<String> result) {
            progressDialog.dismiss();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(CategoryOverviewView.this, R.layout.category_item_text, result);
            ListView listView = (ListView) findViewById(R.id.categoryList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // TODO show pantry item
                }
            });
        }
    }

}