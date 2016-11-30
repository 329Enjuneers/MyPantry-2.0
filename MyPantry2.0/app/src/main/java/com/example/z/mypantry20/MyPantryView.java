package com.example.z.mypantry20;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MyPantryView extends ListActivity
    implements LoaderManager.LoaderCallbacks<Cursor>
{

    private PantryDbHelper dbHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pantry_view);

        Intent intent = getIntent();
        dbHelper = new PantryDbHelper(getApplicationContext());

        // TODO: Update the listview with items in pantry fetched from db.
//        ProgressBar progressBar = new ProgressBar(this);
//        progressBar.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
//                DrawerLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//        progressBar.setIndeterminate(true);
//        getListView().setEmptyView(progressBar);
//
//        // Must add the progress bar to the root of the layout
//        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//        root.addView(progressBar);
//
//        // For the cursor adapter, specify which columns go into which views
//        String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
//        int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1
//
//        // Create an empty adapter we will use to display the loaded data.
//        // We pass null for the cursor, then update it in onLoadFinished()
//        mAdapter = new SimpleCursorAdapter(this,
//                android.R.layout.simple_list_item_1, null,
//                fromColumns, toViews, 0);
//        setListAdapter(mAdapter);
//
//        // Prepare the loader.  Either re-connect with an existing one,
//        // or start a new one.
//        getLoaderManager().initLoader(0, null, this);
    }


    // Called when a new Loader needs to be created
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
                null, null, null, null);
    }

    // Called when a previously created loader has finished loading
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
    }

    // Called when a previously created loader is reset, making the data unavailable
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }

    /**
     * Get all items stored in SQLite db.
     * @return - Returns arraylist of pantry items.
     */
    private ArrayList<PantryItem> getItems(){
        ArrayList<PantryItem> pantryItems = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String[] projections = {PantryContract.Pantry._ID, PantryContract.Pantry.ITEM_NAME, PantryContract .Pantry.ITEM_CATEGORY,
                PantryContract.Pantry.AMOUNT_REMAINING, PantryContract.Pantry.AMOUNT_REMAINING_UNIT};

        Cursor c = db.query(
                PantryContract.Pantry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null
        );
        // Iterate over the results and return.
        c.moveToFirst();
        if(c.getColumnCount() > 0){
            do{

            }while(c.moveToNext());
        }
        c.close();
        return pantryItems;
    }
}