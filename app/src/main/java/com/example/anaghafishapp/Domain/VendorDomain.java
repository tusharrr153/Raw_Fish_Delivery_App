package com.example.anaghafishapp.Domain;

import java.io.Serializable;

public class VendorDomain implements Serializable {
    private String pic;
    private Double price;
    private String title;
    private String name;
    private int numberInCart;

    public VendorDomain() {
    }

    public VendorDomain(String pic, Double price, String title, String name, int numberInCart) {
        this.pic = pic;
        this.price = price;
        this.title = title;
        this.name = name;
        this.numberInCart = numberInCart;
    }

    public String getName() {
        return name;
    }



    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
