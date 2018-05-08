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



    private String mDate;

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

    public String getDate() { return mDate;
    }

    public Book (String image,String author,  String title,String description,String date ) {
        mImage = image;
        mAuthor = author;
        mDescription = description;
        mTitle = title;
        mDate = date;
    }



}
