package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnAdapterClickListener, StepDetailsFragment.ChangeFragment {
    private boolean twoPane = true;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        if (findViewById(R.id.sw600_separator) == null)
            twoPane = false;
        if (savedInstanceState == null){
            Recipe recipe = getIntent().getParcelableExtra(getResources().getString(R.string.PARCELABLE_RECIPE));
            fragmentManager.beginTransaction().add(R.id.activity_recipe_master_list, RecipeDetailsFragment.newInstance(recipe)).commit();
            if (findViewById(R.id.sw600_separator) != null)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setSharedPreference(recipe);
            sendBroadcast();
        }
    }

    @Override
    public void onAdapterClickListener(int clickedItemIndex, Recipe recipe) {
        if (twoPane) {
            if (clickedItemIndex == 0)
                fragmentManager.beginTransaction().replace(R.id.sw600_detailed_pane, ListIngredientsFragment.newInstance(recipe)).commit();
            else {
                StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe, clickedItemIndex - 1, false);
                fragmentManager.beginTransaction().replace(R.id.sw600_detailed_pane, stepDetailsFragment).commit();
            }
        }
        else {
            if (clickedItemIndex == 0) {
                Intent startListIngredientsActivity = new Intent(getApplicationContext(), ListIngredientsActivity.class);
                startListIngredientsActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
                startActivity(startListIngredientsActivity);
            }
            else {
                Intent startStepActivity = new Intent(getApplicationContext(), StepActivity.class);
                startStepActivity.putExtra(getString(R.string.ID),clickedItemIndex - 1);
                startStepActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
                startActivity(startStepActivity);
            }
        }
    }

    public void setSharedPreference(Recipe recipe) {
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getResources().getString(R.string.preference_recipe_id), recipe.getId());
        editor.putString(getResources().getString(R.string.preference_recipe_name), recipe.getName());
        editor.apply();
    }
    public void sendBroadcast() {
        Intent intent = new Intent(this, ListIngredientsWidgetProvider.class);
        intent.setAction(ListIngredientsWidgetProvider.WIDGET_UPDATE);
        sendBroadcast(intent);
    }

    @Override
    public void increaseStep(Recipe recipe, int id, boolean navigationButton) { }

    @Override
    public void decreaseStep(Recipe recipe, int id, boolean navigationButton) { }
}
