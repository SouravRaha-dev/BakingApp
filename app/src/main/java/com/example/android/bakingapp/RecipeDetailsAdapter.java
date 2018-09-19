package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_INGREDIENTS = 0, VIEW_TYPE_STEP = 1;
    private Recipe recipe;
    private Context context;
    final private ListItemClickListener listItemClickListener;
    private List<CardView> cardViewList = new ArrayList<>();
    public RecipeDetailsAdapter(Recipe recipe, ListItemClickListener listItemClickListener, Context context) {
        this.recipe = recipe;
        this.listItemClickListener = listItemClickListener;
        this.context = context;
    }
    public Recipe getRecipe() {
        return recipe;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_INGREDIENTS:
                View view = inflater.inflate(R.layout.recipe_details_ingredients_adapter_item, parent, false);
                return new RecipeDetailsIngredientsViewHolder(view);
            case VIEW_TYPE_STEP:
                View view2 = inflater.inflate(R.layout.recipe_details_step_adapter_item, parent, false);
                return new RecipeDetailsStepViewHolder(view2);
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_INGREDIENTS:
                final RecipeDetailsIngredientsViewHolder ingredientsViewHolder = (RecipeDetailsIngredientsViewHolder) holder;
                ingredientsViewHolder.ingredientsTxtView.setText(R.string.list_ingredients);
                break;
            case VIEW_TYPE_STEP:
                final RecipeDetailsStepViewHolder stepViewHolder = (RecipeDetailsStepViewHolder) holder;
                String str = "Step " + Integer.toString(position - 1);
                stepViewHolder.stepIdTxtView.setText(str);
                stepViewHolder.stepDescriptTxtView.setText(recipe.getSteps().get(position - 1).getShortDescription());
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + getItemViewType(position));
        }
    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_INGREDIENTS;
        else
            return VIEW_TYPE_STEP;
    }

    public class RecipeDetailsIngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ingredients_txtView) TextView ingredientsTxtView;
        @BindView(R.id.recipe_details_view) CardView cardView;
        private RecipeDetailsIngredientsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            cardViewList.add(cardView);
        }

        @Override
        public void onClick(View v) {
            for (CardView cardViewInList : cardViewList){
                cardViewInList.setCardBackgroundColor(context.getResources().getColor(R.color.Second));
            }
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            listItemClickListener.onListItemClicked(getAdapterPosition(), recipe);
        }
    }

    public class RecipeDetailsStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_id_txtView) TextView stepIdTxtView;
        @BindView(R.id.step_descript_txtView) TextView stepDescriptTxtView;
        @BindView(R.id.recipe_details_view) CardView cardView;
        private RecipeDetailsStepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            cardViewList.add(cardView);
        }

        @Override
        public void onClick(View v) {
            for (CardView cardViewInList : cardViewList)
                cardViewInList.setCardBackgroundColor(context.getResources().getColor(R.color.Second));
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            listItemClickListener.onListItemClicked(getAdapterPosition(), recipe);
        }
    }

    public interface ListItemClickListener {
        void onListItemClicked(int clickedItemIndex, Recipe recipe);
    }
}