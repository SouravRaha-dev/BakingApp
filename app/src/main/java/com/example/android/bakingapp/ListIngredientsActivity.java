package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListIngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ingredients);
        if (savedInstanceState == null) {
            Recipe recipe = getIntent().getParcelableExtra(getResources().getString(R.string.PARCELABLE_RECIPE));
            setTitle(recipe.getName());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.activity_list_ingredients_container, ListIngredientsFragment.newInstance(recipe)).commit();
        }
    }
}
