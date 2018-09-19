package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}
class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Recipe recipe;
    public GridRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() { }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() { }

    @Override
    public int getCount() {
        if (recipe == null)
            return 0;
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = recipe.getIngredients().get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        views.setTextViewText(R.id.widget_item_name_ingredient, ingredient.getIngredient());
        views.setTextViewText(R.id.widget_item_quantity, Double.toString(ingredient.getQuantity()));
        views.setTextViewText(R.id.widget_item_measure, ingredient.getMeasure());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void initData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(context.getResources().getString(R.string.preference_recipe_id), 0);
        if (!NetworkUtils.isNetworkAvailable(context) || id == -1 )
            recipe = null;
        else
            recipe = NetworkUtils.getListRecipesFromURL().get(id - 1);
    }
}
