package com.example.sidkathuria14.inventory.models;

import android.widget.ImageView;

/**
 * Created by sidkathuria14 on 8/3/18.
 */

public class Item {
    String name;
    int Quantity;
    String imagePath;
    String description;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item( int id,String name, String description, int quantity) {
        this.name = name;
        Quantity = quantity;
        this.description = description;
        this.id = id;
    }

    public Item(String name, String description, int quantity) {
        this.name = name;
        this.Quantity = quantity;
        this.description = description;
    }

    public Item(int id, String name,  String description,int quantity, String imagePath) {
        this.name = name;
        Quantity = quantity;
        this.imagePath = imagePath;
        this.description = description;
        this.id = id;
    }

    public Item(String name, String description, int quantity, String imagePath) {
        this.name = name;
        this.Quantity = quantity;
        this.imagePath = imagePath;
        this.description = description;
    }

    public Item(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
