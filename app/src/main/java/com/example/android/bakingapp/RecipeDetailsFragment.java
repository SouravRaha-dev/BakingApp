package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.ListItemClickListener {
    OnAdapterClickListener callbacks;
    @BindView(R.id.master_list_recycler) RecyclerView recyclerView;
    public RecipeDetailsFragment() { }
    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("RECIPE", recipe);
        recipeDetailsFragment.setArguments(args);
        return recipeDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_layout, container, false);
        ButterKnife.bind(this, rootView);
        Recipe recipe = getArguments().getParcelable("RECIPE");
        getActivity().setTitle(recipe.getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        RecipeDetailsAdapter recipeDetailsAdapter = new RecipeDetailsAdapter(recipe,this, getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipeDetailsAdapter);
        recyclerView.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onListItemClicked(int clickedItemIndex, Recipe recipe) {
        callbacks.onAdapterClickListener(clickedItemIndex, recipe);
    }
    public interface OnAdapterClickListener{
        void onAdapterClickListener(int clickedItemIndex, Recipe recipe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbacks = (OnAdapterClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnAdapterClickListener");
        }
    }
}
