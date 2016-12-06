package com.example.z.mypantry20;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {


    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        addButton = (Button) findViewById(R.id.addCategoryButton);
        setOnClickListeners();

    }


    public void setOnClickListeners() {
        addButton.setEnabled(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText name = (EditText) findViewById(R.id.name);
                final EditText description = (EditText) findViewById(R.id.description);

                String newCategoryName = name.getText().toString();
                String newCategoryDescription = description.getText().toString();
                if(newCategoryName.equals("")){
                    // Check if category name is empty, if so show an error.
                    name.setError("Please give a name to this category.");
                }else if(newCategoryDescription.equals("")) {
                    description.setError("Please give brief description of this category.");
                }else{
                    // Insert Category in the database.
                    Category newCategory = new Category(null, newCategoryName, newCategoryDescription);
                    new InsertCategoryTask(AddCategoryActivity.this).execute(newCategory);
                }

                //go back to CategoryOverViewView automatically
                finish();
            }
        });
    }

    private class InsertCategoryTask extends AsyncTask<Category, Void, Boolean> {
        private Context context;
        private Category newCategory;
        InsertCategoryTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        protected Boolean doInBackground(Category... s) {
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            newCategory = s[0];
            ContentValues cv = new ContentValues();
            cv.put(PantryContract.Category.NAME, newCategory.getName());
            cv.put(PantryContract.Category.DESCRIPTION, newCategory.getDescription());
            dbHelper.getWritableDatabase().insert(PantryContract.Category.TABLE_NAME, null, cv);
            return true;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(Boolean success) {
            if(success){
                Log.d("Successfully added", "");
                CharSequence text = "Added Category " + newCategory.getName();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            }else{
                Log.d("Adding was unsuccessful", "");
            }
        }
    }
}
