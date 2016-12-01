package com.example.z.mypantry20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
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
        Log.d("Does this work?", "Don't know2");
        setContentView(R.layout.activity_category_overview_view);
        setTitle("MyPantry 2.0 - " + title);

        ArrayList<String> names = fetchCategoryNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.category_item_text, names);
        ListView listView = (ListView) findViewById(R.id.categoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO show pantry item
            }
        });

        addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
        setOnClickListeners();

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


    public ArrayList<String> fetchCategoryNames() {
        ArrayList<String> categories = new ArrayList<String>();
        PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT distinct " + PantryContract.Pantry.ITEM_CATEGORY + " FROM " + PantryContract.Pantry.TABLE_NAME, null);
        c.moveToFirst();
        if (c.getColumnCount() > 0) {
            do {
                categories.add(c.getString(0));
            }while(c.moveToNext());
        }
        c.close();
        return categories;
    }


}