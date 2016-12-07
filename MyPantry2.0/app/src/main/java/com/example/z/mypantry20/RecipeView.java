package com.example.z.mypantry20;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeView extends AppCompatActivity {

    FloatingActionButton addRecipeButton;
    Button consumeItemsButton;
    ArrayList<PantryItem> recipeItems = new ArrayList<>();
    String amount;
    String unit;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        setTitle("Recipe");

        Intent i = getIntent();
        if (i != null) {
            final ListView recipeList = (ListView) findViewById(R.id.recipeList);
            amount = i.getStringExtra("amount");
            unit = i.getStringExtra("unit");
            name = i.getStringExtra("name");

            //TODO add element to view
            //final PantryItem pantryItem = (PantryItem) i.getSerializableExtra("pantryItem");

//            recipeItems.add(pantryItem);
//            ArrayAdapter<PantryItem> adapter = new ArrayAdapter<PantryItem>(RecipeView.this, android.R.layout.simple_list_item_2, android.R.id.text1, recipeItems) {
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    View view = super.getView(position, convertView, parent);
//                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
//
//                    text1.setText(pantryItem.getName());
//                    text2.setText("Amount requested: " + amount + " " + unit);
//                    return view;
//                }
//            };
//            recipeList.setAdapter(adapter);

            addRecipeButton = (FloatingActionButton) findViewById(R.id.addRecipeButton);
            consumeItemsButton = (Button) findViewById(R.id.consumeItemsButton);
            consumeItemsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO update database with new values
                    //TODO consume?
                    ;
                    for (int i = 0; i < recipeItems.size(); i++) {
                        final PantryItem item = (PantryItem) recipeList.getItemAtPosition(i);
                        System.out.println("-------------amount to consume = " + amount);
                        String verdict = item.consume(Float.parseFloat(amount), unit);
                        System.out.println("And the verdict is.... " + verdict);
                        String alertMessage = "";
                        if (verdict.equals("unitMisMatch")) {
                            //units don't match
                            alertMessage = item.getName() + " does not have the correct units. Please change to " + item.getAmountRemainingUnit() + ".";
                            AlertDialog.Builder builder = new AlertDialog.Builder(RecipeView.this);
                            builder.setMessage(alertMessage)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle Ok

                                            Intent intent = new Intent(RecipeView.this, CategoryOverviewView.class);
                                            startActivity(intent);

                                        }
                                    })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // Handle Cancel
//                                    }
//                                })
                                    .create();
                        } else if (verdict.equals("notEnough")) {
                            //not enough of the item
                            alertMessage = item.getName() + " only has " + item.getAmountRemaining() + " " + item.getAmountRemainingUnit() + ". You don't have enough!";
                            AlertDialog.Builder builder = new AlertDialog.Builder(RecipeView.this);
                            builder.setMessage(alertMessage)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle Ok

                                            Intent intent = new Intent(RecipeView.this, CategoryOverviewView.class);
                                            startActivity(intent);

                                        }
                                    })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // Handle Cancel
//                                    }
//                                })
                                    .create().show();

                        } else {
                            CharSequence text = "Consumed " + item.getName() + " successfully. You now have " + item.getAmountRemaining() + " " + item.getAmountRemainingUnit() + " remaining";
                            Toast t = Toast.makeText(RecipeView.this, text, Toast.LENGTH_SHORT);
                            t.show();
                            Intent intent = new Intent(RecipeView.this, CategoryOverviewView.class);
                            startActivity(intent);

                        }
                    }
                }
            });
            setOnClickListeners();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchPantryItemTask().execute();
    }

    private void setOnClickListeners() {
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecipeView.this, AddRecipeActivity.class);
                startActivity(i);
            }
        });


    }

    private class FetchPantryItemTask extends AsyncTask<Void, Void, ArrayList<PantryItem>> {
        private ProgressDialog progressDialog = new ProgressDialog(RecipeView.this);

        public FetchPantryItemTask() {
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait...");
            //progressDialog.show();
        }

        protected ArrayList<PantryItem> doInBackground(Void... s) {
            ArrayList<PantryItem> pantryItems = new ArrayList<>();
            PantryDbHelper dbHelper = new PantryDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT " + PantryContract.Pantry.ITEM_NAME + ", " + PantryContract.Pantry.AMOUNT_REMAINING + ", " + PantryContract.Pantry.AMOUNT_REMAINING_UNIT + " FROM " + PantryContract.Pantry.TABLE_NAME + " WHERE ITEM_NAME=" + name + " AND AMOUNT_REMAINING_UNIT=" + unit, null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    pantryItems.add(new PantryItem(c.getString(c.getColumnIndex(PantryContract.Pantry.ITEM_NAME)),
                            (c.getFloat(c.getColumnIndex(PantryContract.Pantry.AMOUNT_REMAINING))),
                            (c.getString((c.getColumnIndex(PantryContract.Pantry.AMOUNT_REMAINING_UNIT))))));
                } while (c.moveToNext());
            }
            c.close();
            return pantryItems;
        }

        protected void onProgressUpdate(Void... progress) {
            // Nothing onProgress
        }

        protected void onPostExecute(ArrayList<PantryItem> result) {
            progressDialog.dismiss();

            ListView lv = (ListView) findViewById(R.id.recipeList);
            ArrayAdapter<PantryItem> adapter = new ArrayAdapter<PantryItem>(RecipeView.this, android.R.layout.simple_list_item_2, android.R.id.text1, recipeItems) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(name);
                    text2.setText("Amount requested: " + amount + " " + unit);
                    return view;
                }
            };
            lv.setAdapter(adapter);
            lv.setVisibility(View.VISIBLE);
        }
    }

}



