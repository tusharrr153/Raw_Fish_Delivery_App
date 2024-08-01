package com.example.anaghafishapp.Domain;


public class CategoryDomain {

    private String title;
    private int pic;
    public CategoryDomain(String title, int pic) {
        this.title = title;
        this.pic = pic;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title; // Corrected to set the 'title' field
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
