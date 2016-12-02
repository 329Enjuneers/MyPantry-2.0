package com.example.z.mypantry20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.z.mypantry20.R.layout.pantry_item_text;

public class CategoryView extends AppCompatActivity
{

    private ListView lv;
    private Button addPantryItemButton;
    //private ArrayList<PantryItem> pantryItems = new ArrayList<PantryItem>(); ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        addPantryItemButton = (Button) findViewById(R.id.addPantryItemButton);
        setOnClickListeners();
        new FetchPantryItemsTask().execute();


        Intent i = getIntent();
        Bundle extras = i.getExtras();
        Category category = (Category) extras.getSerializable("category");
        setTitle(category.getName());

        TextView description = (TextView) findViewById(R.id.categoryDescription);
        //description.setText(category.getDescription());
        description.setText("Test Description");

        ListView lv = (ListView) findViewById(R.id.pantryItemListView);

        //TODO fill pantryItems from database: fetchPantryItems(category)
        //ArrayList<PantryItem> pantryItems = fetchPantryItems(category);
        //testing
        //pantryItems.add(new PantryItem("milk", 10, "oz"));
        //pantryItems.add(new PantryItem("cheese", 1, "lb"));

//        ArrayList<String> names = new ArrayList<String>();
//        names.add("milk");
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, pantry_item_text, names);

//


        //TODO implement deleting the category when swiping left?
        // http://stackoverflow.com/questions/14398733/remove-item-listview-with-slide-like-gmail

    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchPantryItemsTask().execute();
    }

    public void setOnClickListeners() {
        addPantryItemButton.setEnabled(true);
        addPantryItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryView.this, AddPantryItemActivity.class);
                startActivity(i);
            }
        });
    }

    private class FetchPantryItemsTask extends AsyncTask<Void, Void, ArrayList<PantryItem>> {
        private ProgressDialog progressDialog = new ProgressDialog(CategoryView.this);
        public FetchPantryItemsTask(){
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        protected ArrayList<PantryItem> doInBackground(Void... s) {
            ArrayList<PantryItem> pantryItems = new ArrayList<>();
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //TODO check query
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Pantry.ITEM_NAME + ", " + PantryContract.Pantry.AMOUNT_REMAINING + ", " + PantryContract.Pantry.AMOUNT_REMAINING_UNIT + " FROM " + PantryContract.Pantry.TABLE_NAME + "WHERE CATEGORY_ID="  + PantryContract.Pantry.CATEGORY_ID,null);
            c.moveToFirst();
            if (c.getColumnCount() > 0) {
                do {
                    pantryItems.add(new PantryItem(c.getString(0), c.getFloat(1), c.getString(2)));
                }while(c.moveToNext());
            }
            c.close();
            return pantryItems;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<PantryItem> result) {
            progressDialog.dismiss();
            ArrayAdapter<PantryItem> adapter = new ArrayAdapter<>(CategoryView.this, R.layout.category_item_text, result);
            ListView listView = (ListView) findViewById(R.id.categoryList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent in = new Intent(CategoryView.this, CategoryView.class);
                    //passing category object to CategoryView activity
                    in.putExtra("pantryItem", (PantryItem) adapterView.getItemAtPosition(i));
                    startActivity(in);
                }
            });
        }
    }
}