package com.example.android.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private int id, servings;
    private String name, image;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public List<Step> getSteps() {
        return steps;
    }
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    public int getServings() {
        return servings;
    }
    public void setServings(int servings) {
        this.servings = servings;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String toString() {
        int i;
        StringBuilder str =  new StringBuilder();
        str.append("Step ").append(Integer.toString(id)).append(": ").append(name).append("\n").append("INGREDIENTS:\n");
        for(i = 0; i < ingredients.size(); i++)
            str.append(ingredients.get(i));
        str.append("STEPS:\n");
        for(i = 0; i < steps.size(); i++)
            str.append(steps.get(i));
        str.append("Servings: ").append(Integer.toString(servings)).append(".\n").append("Image: ").append(image).append(".\n");
        return str.toString();
    }
    public Recipe(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        parcel.readTypedList(ingredients, Ingredient.CREATOR);
        parcel.readTypedList(steps, Step.CREATOR);
        this.servings = parcel.readInt();
        this.image = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
