package com.clemcreativity.uzapoint.model;

/**
 * Created by user on 5/8/2016.
 */
public class Product {
    private String code,name,unit,price,category,sub_category,quantity,details;

    public Product(){
    }


    public Product(String name,String code,String quantity){
        this.name = name;
        this.code = code;
        this.quantity = quantity;
    }

    public Product(String code,String name,String unit,String price,String category,String sub_category,String quantity,String details){
        this.name = name;
        this.code = code;
        this.unit = unit;
        this.price = price;
        this.category = category;
        this.sub_category = sub_category;
        this.quantity = quantity;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
