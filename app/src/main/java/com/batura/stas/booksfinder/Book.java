package com.batura.stas.booksfinder;

/**
 * Created by HOME on 28.04.2018.
 */

public class Book {
    //Class for storing information about book
    private String mAuthor;
    private String mDescription;
    private String mTitle;

    public String getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }


    public Book (String author, String description, String title ) {
        mAuthor = author;
        mDescription = description;
        mTitle = title;
    }



}
