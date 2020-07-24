package com.example.myrecipe;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class FoodData  implements Serializable {
    public String getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(String preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    @ColumnInfo(name = "preparationMinutes")
    private String preparationMinutes;
    @ColumnInfo(name = "itemName")
    private String itemName;
    @ColumnInfo(name = "itemDescription")
    private String itemDescription;
    @ColumnInfo(name = "ItemImage")
    private String ItemImage;

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    @PrimaryKey(autoGenerate = true)
    private int idRecipe;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "ingredients")
    private String ingredients;
    @ColumnInfo(name = "instructions")
    private String instructions;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @ColumnInfo(name = "rating")
    private float rating = 0;

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemImage(String itemImage) {
        ItemImage = itemImage;
    }

    public FoodData() {
    }

//    public FoodData(String itemName, String itemDescription, String itemImage, String id, String ingredients, String instructions) {
//        this.itemName = itemName;
//        this.itemDescription = itemDescription;
//        ItemImage = itemImage;
//        this.id = id;
//        this.ingredients = ingredients;
//        this.instructions = instructions;
//    }
    public FoodData(String itemName, String itemDescription, String itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.ItemImage = itemImage;

    }



    public FoodData(String itemName, String itemDescription, String itemImage,String PreparationMinutes) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.ItemImage = itemImage;
        this.preparationMinutes=PreparationMinutes;

    }
    public FoodData(String itemName, String itemDescription, String itemImage,String PreparationMinutes,String id) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.ItemImage = itemImage;
        this.preparationMinutes=PreparationMinutes;
        this.id=id;

    }
    public FoodData (String itemName, String itemDescription, String itemImage,String PreparationMinutes,String id,String instructions){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.ItemImage = itemImage;
        this.preparationMinutes=PreparationMinutes;
        this.id=id;
        this.instructions=instructions;


    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }



    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemImage() {
        return ItemImage;
    }



}
