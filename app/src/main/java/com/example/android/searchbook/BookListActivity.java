package com.example.android.searchbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        BookAdapter itemsAdapter = new BookAdapter(this, books);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

    }
}
