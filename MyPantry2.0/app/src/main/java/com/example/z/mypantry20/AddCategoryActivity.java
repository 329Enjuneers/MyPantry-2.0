package com.example.z.mypantry20;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {


    Button addButton;
    ArrayList<Category> categories = new ArrayList<Category>();

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

                //create empty pantry item list for category constructor
                ArrayList<PantryItem> pantryItemList = new ArrayList<PantryItem>();
                pantryItemList.add(new PantryItem("milk", 10, "oz"));

                Category newCategory = new Category(pantryItemList, name.getText().toString(), description.getText().toString());
                categories.add(newCategory);
                System.out.println("category is " + categories.get(0).toString());

                //show user a toast to confirm it was added
                Context context = getApplicationContext();
                CharSequence text = "Added Category " + name.getText().toString();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();

                //go back to CategoryOverViewView automatically
                finish();

            }
        });
    }
}
