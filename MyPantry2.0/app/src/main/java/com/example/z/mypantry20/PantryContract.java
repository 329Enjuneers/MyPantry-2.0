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
        public static final String ITEM_CATEGORY = "category";
        public static final String AMOUNT_REMAINING = "quantity";
        public static final String AMOUNT_REMAINING_UNIT = "units";
    }
}
