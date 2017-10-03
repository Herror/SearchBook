package com.example.android.searchbook;

import android.content.Intent;
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

public class BookListActivity extends AppCompatActivity {

    private static final String LOG_TAG = BookListActivity.class.getName();

    private static final String GOOGLE_BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    //create an instance of the BookAdapter so I can access and modify it
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //get the text the user typed into the editText
        Intent intent = getIntent();
        String message = intent.getStringExtra(BookActivity.EXTRA_MESSAGE);

        //find the reference of the gridView in the layout
        GridView bookGridView = (GridView) findViewById(R.id.list);
        //create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        //set the adapter on the gridView so it can be populated
        bookGridView.setAdapter(mAdapter);

        BookAsyncTask bookAsyncTask = new BookAsyncTask();
        //execute the task and add the search message
        bookAsyncTask.execute(GOOGLE_BOOK_URL + message);

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

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> result) {
            //clear the adapter from previous book data
            mAdapter.clear();

            if (result != null && !result.isEmpty()) {
                mAdapter.addAll(result);
            }

            ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
            spinner.setVisibility(View.GONE);
        }
    }
}
