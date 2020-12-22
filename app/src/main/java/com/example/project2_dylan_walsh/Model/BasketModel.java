package com.example.project2_dylan_walsh.Model;





public class BasketModel {
    String Name,Price,Key,Code;



    public BasketModel(String name, String price, String key, String code) {
        Name = name;
        Price = price;
        Key = key;
        Code = code;
    }
    public  BasketModel ()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
