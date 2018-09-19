package com.example.android.bakingapp;


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

public class ListIngredientsFragment extends Fragment {

    @BindView(R.id.list_ingredients_recyclerview) RecyclerView recyclerView;
    public ListIngredientsFragment() { }
    public static ListIngredientsFragment newInstance(Recipe recipe) {
        ListIngredientsFragment listIngredientsFragment = new ListIngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable("RECIPE", recipe);
        listIngredientsFragment.setArguments(args);
        return listIngredientsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_ingredients, container, false);
        ButterKnife.bind(this, rootView);
        Recipe recipe = getArguments().getParcelable("RECIPE");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(recipe);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setHasFixedSize(true);
        return rootView;
    }
}
