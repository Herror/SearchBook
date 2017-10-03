package com.example.android.searchbook;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by enach on 10/4/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    @Override
    public List<Book> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }

        List<Book> result = QueryUtils.fetchBookData(mUrl);
        return result;
    }
}
