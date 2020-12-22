package com.example.project2_dylan_walsh.Model;

import android.graphics.Bitmap;
import android.widget.TextView;

public class Model {
    Bitmap image;
    String name,price,code;

    public Model(Bitmap image, String name, String price, String code) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.code = code;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
