package com.example.z.mypantry20;

import android.provider.BaseColumns;

/**
 * Created by Rushabh on 11/26/16.
 */

public final class PantryContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PantryContract() {}

    /* Inner class that defines the table contents */
    public static class Pantry implements BaseColumns {
        public static final String TABLE_NAME = "pantry_items";
        public static final String ITEM_NAME = "item";
        public static final String CATEGORY_ID = "category_id";
        public static final String AMOUNT_REMAINING = "quantity";
        public static final String AMOUNT_REMAINING_UNIT = "units";
    }


    /* Inner class that defines the table contents */
    public static class Category implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }

    public static class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final String PANTRY_ITEM_ID = "pantry_item_id";
        public static final String AMOUNT = "amount";
    }
}
