package com.example.android.searchbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class BookActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.searchbook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookListActivity = new Intent(BookActivity.this, BookListActivity.class);
                EditText editText = (EditText) findViewById(R.id.book_search);
                String message = editText.getText().toString();
                bookListActivity.putExtra(EXTRA_MESSAGE, message);
                startActivity(bookListActivity);
            }
        });
    }
}
