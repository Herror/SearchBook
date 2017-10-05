package com.example.android.searchbook;

import java.util.ArrayList;

/**
 * Created by enach on 9/26/2017.
 */

public class Book {

    //title of the book
    private String mTitle;

    //Author of the book
    private ArrayList<String> mAuthor;

    //Url of the preview of the book
    private String mUrl;

    //number of pages
    private String mPages;

    /**
     * @param title - The title of the book
     * @param author - The author of the book
     * @param url - The url for the sample book
     */
    public Book(String title, ArrayList<String> author, String url, String pages){
        mTitle = title;
        mAuthor = author;
        mUrl = url;
        mPages = pages;
    }

    //return the title of the book
    public String getTitle(){
        return mTitle;
    }
    //return the author of the book
    public ArrayList<String> getAuthor(){
        return mAuthor;
    }
    //return the preview Url
    public String getUrl(){
        return mUrl;
    }
    //return the number of pages as a String
    public String getPages(){
        return mPages;
    }

}
