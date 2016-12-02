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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.z.mypantry20.R.layout.pantry_item_text;

public class CategoryView extends AppCompatActivity
{

    private FloatingActionButton addPantryItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        new FetchPantryItemsTask().execute();

        addPantryItemButton = (FloatingActionButton) findViewById(R.id.addPantryItemButton);
        setOnClickListeners();

        PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
        Log.d("Successfully created : ", dbHelper.getDatabaseName());
        dbHelper.close();

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        Category category = (Category) extras.getSerializable("category");
        setTitle(category.getName());

        TextView description = (TextView) findViewById(R.id.categoryDescription);
        description.setText(category.getDescription());

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
        ArrayAdapter<PantryItem> adapter;
        ListView lv = (ListView) findViewById(R.id.pantryItemListView);
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
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Pantry.ITEM_NAME + ", " + PantryContract.Pantry.AMOUNT_REMAINING + ", " + PantryContract.Pantry.AMOUNT_REMAINING_UNIT + " FROM " + PantryContract.Pantry.TABLE_NAME + " WHERE CATEGORY_ID="  + PantryContract.Pantry.CATEGORY_ID,null);
            c.moveToFirst();
            //TODO this query returns nothing!
            System.out.println("pantryItem name is " + c.getString(c.getColumnIndex(PantryContract.Pantry.ITEM_NAME)));

            if (c.getCount() > 0) {
                do {

                    pantryItems.add(new PantryItem(c.getString(c.getColumnIndex(PantryContract.Pantry.ITEM_NAME)),
                            (c.getFloat(c.getColumnIndex(PantryContract.Pantry.AMOUNT_REMAINING))),
                            (c.getString((c.getColumnIndex(PantryContract.Pantry.AMOUNT_REMAINING_UNIT))))));
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
            if(result.size() > 0){
                adapter = new ArrayAdapter<>(CategoryView.this, R.layout.pantry_item_text, result);
                lv.setAdapter(adapter);
                lv.setVisibility(View.VISIBLE);
                (findViewById(R.id.no_items_error)).setVisibility(View.INVISIBLE);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(CategoryView.this, PantryItemView.class);
                        //passing pantryItem object to PantryItemView activity
                        in.putExtra("pantryItem", (PantryItem) adapterView.getItemAtPosition(i));
                        startActivity(in);
                        //TODO reload list view to show new item?
                        adapter.notifyDataSetChanged();
                    }
                });

            }
            else {
                lv.setVisibility(View.INVISIBLE);
                String error = "You do not have any pantry items yet.";
                ((TextView) findViewById(R.id.no_items_error)).setText(error);
                (findViewById(R.id.no_items_error)).setVisibility(View.VISIBLE);
            }
        }
    }
}