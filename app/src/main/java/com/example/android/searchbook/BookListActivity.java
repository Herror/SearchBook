package com.example.android.searchbook;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;

public class BookListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>>{

    private static final String LOG_TAG = BookListActivity.class.getName();

    private static final String GOOGLE_BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    //Id of the loader
    private static final int BOOK_LOADER_ID = 1;

    //create an instance of the BookAdapter so I can access and modify it
    private BookAdapter mAdapter;

    //reference to the empty message
    private TextView mEmptyStateTextView;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        if(isNetworkAvailable() == true){
            //get a reference to the LoaderManager, in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();

            //initialize the loader
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
            loadingSpinner.setVisibility(View.GONE);
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            mEmptyStateTextView.setText("No internet connection");
        }

        //find the reference of the ListView in the layout
        ListView bookGridView = (ListView) findViewById(R.id.list);
        //create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        //set the adapter on the gridView so it can be populated
        bookGridView.setAdapter(mAdapter);


        /**
         * Create an Intent so that when the user clicks on any of the displayed books
         * it will open a page with a preview of the book
         */

        bookGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Find the current book that it is clicked on
                Book currentBook = mAdapter.getItem(position);

                //convert the String url from the Book class into a URI object
                Uri bookUri = Uri.parse(currentBook.getUrl());

                //create a new intent for the book Uri
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                //Sent the intent to launch the new activity
                startActivity(websiteIntent);
            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        //get the text the user typed into the editText
        Intent intent = getIntent();
        String userInput = intent.getStringExtra(BookActivity.EXTRA_MESSAGE);

            //create a new loader for the given URL
            return new BookLoader(this, GOOGLE_BOOK_URL + userInput);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        //clear the adapter from previous book data
        mAdapter.clear();

        Intent intent = getIntent();
        String userInput = intent.getStringExtra(BookActivity.EXTRA_MESSAGE);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }else if(userInput.isEmpty()){
            mEmptyStateTextView.setText(R.string.empty_user_imput);
        } else {
            //if there aren't any results to be displayed - return the below message
            mEmptyStateTextView.setText(R.string.no_results);
        }
        //Object for the spinner while loading
        ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        //Loader reset, so we can clear out the existing data
        mAdapter.clear();
    }
}
