package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Recipe> listRecipes;
    private Context context;
    final private ListItemClickListener listItemClickListener;
    public CardAdapter(List<Recipe> listRecipes, Context context, ListItemClickListener listItemClickListener) {
        this.listRecipes = listRecipes;
        this.context = context;
        this.listItemClickListener = listItemClickListener;
    }
    public List<Recipe> getListRecipes() {
        return listRecipes;
    }
    public void setListRecipes(List<Recipe> listRecipes) {
        this.listRecipes = listRecipes;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String urlImage = listRecipes.get(position).getImage();
        if (urlImage.isEmpty())
            holder.cardImgView.setImageResource(R.drawable.question_marks);
        else
            Picasso.get().load(urlImage).placeholder(R.color.colorBlackish).error(R.color.colorBlackish).into(holder.cardImgView);
        holder.cardTxtView.setText(listRecipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listRecipes.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_imgView) ImageView cardImgView;
        @BindView(R.id.card_txtView) TextView cardTxtView;
        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClicked(clickedPosition);
        }
    }
    public interface ListItemClickListener {
        void onListItemClicked(int clickedPosition);
    }
}
