package com.batura.stas.booksfinder;

/**
 * Created by HOME on 28.04.2018.
 */

public class Book {


    //Class for storing information about book
    private String mImage;
    private String mAuthor;
    private String mDescription;
    private String mTitle;

    public String getImage() { return mImage;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }


    public Book (String image,String author, String description, String title ) {
        mImage = image;
        mAuthor = author;
        mDescription = description;
        mTitle = title;
    }



}
