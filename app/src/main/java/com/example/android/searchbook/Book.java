package com.example.android.searchbook;

/**
 * Created by enach on 9/26/2017.
 */

public class Book {

    //cover image of the book
    private int mCoverImage;

    //title of the book
    private String mTitle;

    //Author of the book
    private String mAuthor;

    //Url of the preview of the book

    private String mUrl;

    /**
     * @param coverImage - Cover image of the book from the JSON
     * @param title - The title of the book
     * @param author - The author of the book
     */

    public Book(int coverImage, String title, String author, String url){
        mCoverImage = coverImage;
        mTitle = title;
        mAuthor = author;
        mUrl = url;
    }

    //Just for tests - remove after
    public Book(String title, String author){
        mTitle = title;
        mAuthor = author;
    }

    //Again tests
    public Book(String title){
        mTitle = title;
    }

    //return the image URL
    public int getCoverImage(){
        return mCoverImage;
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
