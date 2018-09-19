package com.example.android.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private Recipe recipe;
    public IngredientsAdapter(Recipe recipe) {
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_ingredients_adapter_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.nameTxtView.setText(recipe.getIngredients().get(position).getIngredient());
        holder.quantityTxtView.setText(Double.toString(recipe.getIngredients().get(position).getQuantity()));
        holder.measureTxtView.setText(recipe.getIngredients().get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        return recipe.getIngredients().size();
    }
    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredients_txtView) TextView nameTxtView;
        @BindView(R.id.ingredients_txtView2) TextView quantityTxtView;
        @BindView(R.id.ingredients_txtView3) TextView measureTxtView;
        private IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
