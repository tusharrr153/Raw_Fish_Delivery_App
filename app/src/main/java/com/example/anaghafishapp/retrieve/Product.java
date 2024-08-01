package com.example.anaghafishapp.retrieve;

public class Product {
    private String image_url;
    private String phone_number;
    private String price;
    private String product_name;
    private String vendor_name;


    public Product() {
    }

    public Product(String image_url, String phone_number, String price, String product_name, String vendor_name) {
        this.image_url = image_url;
        this.phone_number = phone_number;
        this.price = price;
        this.product_name = product_name;
        this.vendor_name = vendor_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }
}

