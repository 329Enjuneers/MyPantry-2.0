package com.example.z.mypantry20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CategoryOverviewView extends AppCompatActivity
{
    String title = "Categories";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview_view);
        setTitle("MyPantry 2.0 - " + title);
    }
}