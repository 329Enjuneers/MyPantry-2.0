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
import android.util.Log;
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

    private Button addButton;
    private int categoryId;

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

                // We came here from an unknown place finish() up this activity now.
                if(categoryId == -1){
                    finish();
                }

                final EditText name = (EditText) findViewById(R.id.name);
                final EditText startingAmount = (EditText) findViewById(R.id.startingAmount);
                final EditText unit = (EditText) findViewById(R.id.unit);

                String newName = name.getText().toString();
                String newUnit = unit.getText().toString();

                if(newName.equals("")){
                    // Check if category name is empty, if so show an error.
                    name.setError("Name of this item.");
                }else if(startingAmount.getText().toString().equals("")) {
                    startingAmount.setError("Starting amount for this item.");
                }else if(newUnit.equals("")) {
                    unit.setError("Units of this item.");
                }else{
                    // Get the float amount for this item.
                    Float newAmount = Float.parseFloat(startingAmount.getText().toString());
                    // Insert Item in the database.
                    PantryItem newItem = new PantryItem(newName, newAmount, newUnit);
                    new InsertPantryItemTask(AddPantryItemActivity.this).execute(newItem);
                    //go back to CategoryOverViewView automatically
                    finish();
                }
            }
        });
    }

    private class InsertPantryItemTask extends AsyncTask<PantryItem, Void, Boolean> {
        private Context context;
        private PantryItem newItem;

        InsertPantryItemTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        protected Boolean doInBackground(PantryItem... s) {
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            newItem = s[0];
            ContentValues cv = new ContentValues();
            cv.put(PantryContract.Pantry.ITEM_NAME, newItem.getName());
            cv.put(PantryContract.Pantry.CATEGORY_ID, categoryId);
            cv.put(PantryContract.Pantry.AMOUNT_REMAINING, newItem.getAmountRemaining());
            cv.put(PantryContract.Pantry.AMOUNT_REMAINING_UNIT, newItem.getAmountRemainingUnit());
            dbHelper.getWritableDatabase().insert(PantryContract.Pantry.TABLE_NAME, null, cv);
            return true;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(Boolean success) {
            if(success){
                Log.d("Successfully added", "");
                //show user a toast to confirm it was added
                CharSequence text = "Added Pantry Item " + newItem.getName();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            }else{
                Log.d("Adding was unsuccessful", "");
            }
        }
    }

}
