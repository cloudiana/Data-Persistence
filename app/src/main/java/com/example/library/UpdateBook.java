package com.example.library;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBook extends AppCompatActivity {

    EditText title_input,author_input,description_input,category_input,publisher_input;
    Button okbtn,notokbtn;

    String id,title,author,category,publisher,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        title_input = (EditText) findViewById(R.id.update_booktitle);
        author_input = (EditText) findViewById(R.id.update_authorname);
        description_input = (EditText) findViewById(R.id.update_description);
        category_input = (EditText) findViewById(R.id.update_category);
        publisher_input = (EditText) findViewById(R.id.update_publisher);


        getAndsetIntentData();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
        okbtn = (Button) findViewById(R.id.updatethistolibrary);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB db = new DB(UpdateBook.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                category = category_input.getText().toString().trim();
                publisher = publisher_input.getText().toString().trim();
                description = description_input.getText().toString().trim();
                db.updateData(id,title,author,category,publisher,description);
                Intent intent = new Intent(UpdateBook.this, MainActivity.class);
                startActivity(intent);
            }
        });

        notokbtn = (Button) findViewById(R.id.deletethistolibrary);
        notokbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndsetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author")
                && getIntent().hasExtra("category") && getIntent().hasExtra("publisher")
                && getIntent().hasExtra("description")){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            category = getIntent().getStringExtra("category");
            publisher = getIntent().getStringExtra("publisher");
            description = getIntent().getStringExtra("description");

            title_input.setText(title);
            author_input.setText(author);
            category_input.setText(category);
            publisher_input.setText(publisher);
            description_input.setText(description);


        }else{
            Toast.makeText(this,"No Data", Toast.LENGTH_LONG).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title +"?");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Are you sure to delete "+ title +"?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB db = new DB(UpdateBook.this);
                db.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}