package com.example.z.mypantry20;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPantryItemActivity extends AppCompatActivity {

    Button addButton;
    ArrayList<PantryItem> pantryItems = new ArrayList<PantryItem>();
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pantry_item);

        Intent i = getIntent();
        categoryId = i.getIntExtra("category_id", -1);

        addButton = (Button) findViewById(R.id.addPantryItemButton);
        setOnClickListeners();
    }


    public void setOnClickListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());

                final EditText name = (EditText) findViewById(R.id.name);
                final EditText startingAmount = (EditText) findViewById(R.id.startingAmount);
                final EditText unit = (EditText) findViewById(R.id.unit);

                //create empty pantry item list for category constructor
                //ArrayList<PantryItem> pantryItemList = new ArrayList<PantryItem>();
                //pantryItemList.add(new PantryItem("milk", 10, "oz"));

                String newName = name.getText().toString();
                Float newAmount = Float.parseFloat(startingAmount.getText().toString());
                String newUnit = unit.getText().toString();

                PantryItem newItem = new PantryItem(newName, newAmount, newUnit);
                pantryItems.add(newItem);

                System.out.println("pantry item 0 is " + pantryItems.get(0).toString());

                ContentValues cv = new ContentValues();
                cv.put(PantryContract.Pantry.ITEM_NAME, newName);
                cv.put(PantryContract.Pantry.CATEGORY_ID, categoryId);
                cv.put(PantryContract.Pantry.AMOUNT_REMAINING, newAmount);
                cv.put(PantryContract.Pantry.AMOUNT_REMAINING_UNIT, newUnit);

                dbHelper.getWritableDatabase().insert(PantryContract.Pantry.TABLE_NAME, null, cv);

                //show user a toast to confirm it was added
                Context context = getApplicationContext();
                CharSequence text = "Added Pantry Item " + name.getText().toString();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();

                //go back to CategoryView automatically
                finish();

            }
        });
    }

    //TODO this isn't being added to list view in CategoryView.
    // Task needs to be implement.
    private class AddPantryItemsTask extends AsyncTask<PantryItem, Void, ArrayList<PantryItem>> {
        public AddPantryItemsTask(){
        }

        protected ArrayList<PantryItem> doInBackground(PantryItem... s) {
            return null;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<PantryItem> result) {
        }
    }

}
