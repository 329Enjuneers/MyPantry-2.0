package com.example.z.mypantry20;

import android.app.ProgressDialog;
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

        setTitle(title);
        addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
        setOnClickListeners();
        PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
        Log.d("Successfully created : ",dbHelper.getDatabaseName());
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

    private class FetchCategoryTask extends AsyncTask<Void, Void, ArrayList<Category>> {
        private ProgressDialog progressDialog = new ProgressDialog(CategoryOverviewView.this);
        public FetchCategoryTask(){
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
            if (c.getColumnCount() > 0) {
                do {
                    categories.add(new Category(new ArrayList<PantryItem>(), c.getString(0), c.getString(1)));
                }while(c.moveToNext());
            }
            c.close();
            return categories;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<Category> result) {
            progressDialog.dismiss();
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(CategoryOverviewView.this, R.layout.category_item_text, result);
            ListView listView = (ListView) findViewById(R.id.categoryList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent in = new Intent(CategoryOverviewView.this, CategoryView.class);
                    //passing category object to CategoryView activity
                    in.putExtra("category", (Category) adapterView.getItemAtPosition(i));
                    startActivity(in);
                }
            });
        }
    }
}
