package com.example.Samar.capstonetwo.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_  implements Serializable {

    private String Title;
    private String Author;
    private String Image;
    private String id;

    public Data_(){}

    public Data_(String title, String author, String image, String id) {
        Title = title;
        Author = author;
        Image = image;
        this.id = id;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}