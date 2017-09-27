package com.example.android.searchbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        final ArrayList<Book> books = new ArrayList<Book>();

        books.add(new Book("Title", "Author"));
        books.add(new Book("Title1", "Author1"));
        books.add(new Book("Title2", "Author2"));
        books.add(new Book("Title3", "Author3"));
        books.add(new Book("Title4", "Author4"));
        books.add(new Book("Title5", "Author5"));
        books.add(new Book("Title6", "Author6"));


        BookAdapter itemsAdapter = new BookAdapter(this, books);

        GridView gridView = (GridView) findViewById(R.id.list);
        gridView.setAdapter(itemsAdapter);

    }
}
