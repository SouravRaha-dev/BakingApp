package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepActivity extends AppCompatActivity implements StepDetailsFragment.ChangeFragment {
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_step);
        if (savedInstanceState == null) {
            Recipe recipe = getIntent().getParcelableExtra(getResources().getString(R.string.PARCELABLE_RECIPE));
            setTitle(recipe.getName());
            int id = getIntent().getIntExtra(getString(R.string.ID), 0);
            fragmentManager.beginTransaction().add(R.id.activity_step_container, StepDetailsFragment.newInstance(recipe, id, true)).commit();
        }
    }

    @Override
    public void increaseStep(Recipe recipe, int id, boolean navigationButton) {
        StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe, id + 1, navigationButton);
        fragmentManager.beginTransaction().replace(R.id.activity_step_container, stepDetailsFragment).commit();
    }

    @Override
    public void decreaseStep(Recipe recipe, int id, boolean navigationButton) {
        StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe, id - 1, navigationButton);
        fragmentManager.beginTransaction().replace(R.id.activity_step_container, stepDetailsFragment).commit();
    }
}
