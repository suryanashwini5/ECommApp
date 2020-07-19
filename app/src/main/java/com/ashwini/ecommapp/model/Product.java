package com.ashwini.ecommapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Item name")
    @Expose
    private String itemName;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Image name")
    @Expose
    private String imageName;
    @SerializedName("quantity")
    @Expose
    private String quantity;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}


