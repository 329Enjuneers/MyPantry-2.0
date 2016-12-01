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
import java.util.List;


public class CategoryOverviewView extends AppCompatActivity {
    String title = "Categories";
    Button addCategoryButton;
    ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview_view);
        setTitle("MyPantry 2.0 - " + title);

        //NOTE changed from string to Category

//      ArrayList<String> names = fetchCategoryNames();
        ArrayList<Category> names = fetchCategories();
//      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.category_item_text, names);
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, R.layout.category_item_text, names);

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

        addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
        setOnClickListeners();

    }


    @Override
    protected void onResume() {
        super.onResume();
//      ArrayList<String> names = fetchCategoryNames();
        ArrayList<Category> names = fetchCategories();
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, R.layout.category_item_text, names);
        //NOTE changed from string to Category


//      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.category_item_text, names);

        final ListView listView = (ListView) findViewById(R.id.categoryList);
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

    //NOTE changed from string to Category
    public ArrayList<Category> fetchCategories() {
//        ArrayList<String> categories = new ArrayList<String>();
        ArrayList<Category> categories = new ArrayList<Category>();

        PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT distinct " + PantryContract.Pantry.ITEM_CATEGORY + " FROM " + PantryContract.Pantry.TABLE_NAME, null);
        c.moveToFirst();
        if (c.getColumnCount() > 0) {
            do {

                String name = c.getString(0);
                //TODO don't have access to category description in database?: for now just using test string
                String desc = "*This would be description of category*";
                //TODO do we need to initalize the Pantry Item's list for each category here?
                categories.add(new Category(new ArrayList<PantryItem>(), name, desc));
//              categories.add(c.getString(0));
            }while(c.moveToNext());
        }
        c.close();
        return categories;
    }


}