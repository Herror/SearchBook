package com.example.android.searchbook;

/**
 * Created by enach on 9/26/2017.
 */

public class Book {

    //title of the book
    private String mTitle;

    //Author of the book
    private String mAuthor;

    //Url of the preview of the book
    private String mUrl;

    /**
     * @param title - The title of the book
     * @param author - The author of the book
     * @param url - The url for the sample book
     */
    public Book(String title, String author, String url){
        mTitle = title;
        mAuthor = author;
        mUrl = url;
    }

    //return the title of the book
    public String getTitle(){
        return mTitle;
    }
    //return the author of the book
    public String getAuthor(){
        return mAuthor;
    }
    //return the preview Url
    public String getUrl(){
        return mUrl;
    }

}
