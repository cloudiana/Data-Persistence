package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookEditor extends AppCompatActivity {

    EditText title_input,author_input,description_input,category_input,publisher_input;
    Button okbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookeditor);


        title_input = (EditText) findViewById(R.id.booktitle);
        author_input = (EditText) findViewById(R.id.authorname);
        description_input = (EditText) findViewById(R.id.description);
        category_input = (EditText) findViewById(R.id.category);
        publisher_input = (EditText) findViewById(R.id.publisher);


        okbtn = (Button) findViewById(R.id.addtolibrary);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = title_input.getText().toString();
                String author = author_input.getText().toString();
                String description = description_input.getText().toString();
                String category = category_input.getText().toString();
                String publisher = publisher_input.getText().toString();
                if (title.matches("")) {
                    title_input.setText("Unknown");
                }
                if (author.matches("")) {
                    author_input.setText("Unknown");
                }
                if (description.matches("")) {
                    description_input.setText("Unknown");
                }
                if (category.matches("")) {
                    category_input.setText("Unknown");
                }
                if (publisher.matches("")) {
                    publisher_input.setText("Unknown");
                }

                DB db = new DB(BookEditor.this);
                db.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        description_input.getText().toString().trim(),
                        category_input.getText().toString().trim(),
                        publisher_input.getText().toString().trim());
                Intent intent = new Intent(BookEditor.this, MainActivity.class);
                startActivity(intent);




            }
        });

    }
}