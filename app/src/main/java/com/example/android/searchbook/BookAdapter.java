package com.example.android.searchbook;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by enach on 9/26/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, ArrayList<Book> book) {
        super(context, 0, book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View bookItemView = convertView;
        //Check to see if there is an existing listItem view that we can re-use
        //otherwise inflate a new one
        if (bookItemView == null) {
            bookItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list, parent, false);
        }

        //get the position of each item in the ListView
        Book bookPosition = getItem(position);

        //find the TextView with the ID @title
        TextView titleTextView = (TextView) bookItemView.findViewById(R.id.title);
        //set the title of the book from the JSON
        titleTextView.setText(bookPosition.getTitle());

        //find the TextView with the ID @author
        TextView authorTextView = (TextView) bookItemView.findViewById(R.id.author);
        //set the author name of the book from the JSON
        authorTextView.setText(bookPosition.getAuthor());

        return bookItemView;
    }
}
