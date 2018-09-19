package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CardAdapter.ListItemClickListener {

    @BindView(R.id.card_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.error_view) TextView noInternetTxtView;
    @BindView(R.id.refresh_button) Button mRefreshButton;
    @BindView(R.id.progress_bar) ProgressBar mNetworkLoadProgressBar;
    private CardAdapter cardAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setCardAdapter();
        setSharedPreference();
        sendBroadcast();
        mRefreshButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setCardAdapter();
                setSharedPreference();
                sendBroadcast();            }
        });
    }
    public void setCardAdapter() {
        int spanCount;
        List<Recipe> listRecipes = new ArrayList<>();
        if (!NetworkUtils.isNetworkAvailable(this))
            displayNoInternetConnection();
        else {
            listRecipes = NetworkUtils.getListRecipesFromURL();
            displayData();
        }
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            spanCount = getResources().getInteger(R.integer.column_span_portrait);
        else
            spanCount = getResources().getInteger(R.integer.column_span_landscape);
        gridLayoutManager = new GridLayoutManager(this, spanCount);
        cardAdapter = new CardAdapter(listRecipes, this, this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cardAdapter);
    }
    public void displayNoInternetConnection() {
        recyclerView.setVisibility(View.GONE);
        noInternetTxtView.setVisibility(View.VISIBLE);
    }
    public void displayData() {
        recyclerView.setVisibility(View.VISIBLE);
        noInternetTxtView.setVisibility(View.GONE);
        mNetworkLoadProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onListItemClicked(int clickedPosition) {
        Recipe recipe = cardAdapter.getListRecipes().get(clickedPosition);
        Context context = MainActivity.this;
        Class destinationClass = RecipeDetailsActivity.class;
        Intent startRecipeActivity = new Intent(context, destinationClass);
        startRecipeActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
        startActivity(startRecipeActivity);
    }
    public void setSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getResources().getString(R.string.preference_recipe_id), -1);
        editor.apply();
    }
    public void sendBroadcast() {
        Intent intent = new Intent(this, ListIngredientsWidgetProvider.class);
        intent.setAction(ListIngredientsWidgetProvider.WIDGET_UPDATE);
        sendBroadcast(intent);
    }
}
