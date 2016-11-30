package com.example.z.mypantry20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class CategoryOverviewView extends AppCompatActivity
{
    String title = "Categories";
    Button addCategoryButton;
    ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview_view);
        setTitle("MyPantry 2.0 - " + title);

        addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
        setOnClickListeners();

    }

    public void setOnClickListeners()
    {
        addCategoryButton.setEnabled(true);
        addCategoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                Intent i = new Intent(CategoryOverviewView.this, AddCategoryActivity.class);
                startActivity(i);

                //TODO pop up window for user to type in name, description then adds a category
//                LayoutInflater factory = LayoutInflater.from(getBaseContext());
//                final View textEntryView = factory.inflate(R.layout.new_category_prompt, null);
//                final EditText name = (EditText) textEntryView.findViewById(R.id.name);
//                final EditText description = (EditText) textEntryView.findViewById(R.id.description);
//                name.setText("Name", TextView.BufferType.EDITABLE);
//                description.setText("Description", TextView.BufferType.EDITABLE);
//                final AlertDialog.Builder alert = new AlertDialog.Builder(getBaseContext());
//
//                alert.setTitle("Add a new Category")
//                        .setView(textEntryView)
//                        .setPositiveButton("Save",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        Log.i("AlertDialog","TextEntry 1 Entered "+ name.getText().toString());
//                                        Log.i("AlertDialog","TextEntry 2 Entered "+ description.getText().toString());
//
//                                         /* User clicked OK so do some stuff */
//
//                                        Context context = getApplicationContext();
//                                        CharSequence text = "Added Category";
//                                        Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//                                        t.show();
//
//                                        //TODO create pantry item list for category constructor?
////                                        List<PantryItem> pantryItemList = new List<PantryItem>();
////                                        pantryItemList.add(new PantryItem("milk", 10, "oz"));
//
//
//                                        Category newCategory = new Category(null, name.getText().toString(), description.getText().toString());
//                                        categories.add(newCategory);
//                                    }
//                                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                    }
//                                });
//                alert.show();

            }
        });
    }




}