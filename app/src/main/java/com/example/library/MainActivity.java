package com.example.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton button;
    DB db;
    ArrayList<String> book_id, book_title, book_author, book_description, book_category, book_publisher;
    CustomAdapter adapter;
    RecyclerView recyclerView;

    ImageView nodata_image;
    TextView nodata_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        nodata_image = findViewById(R.id.noDataImg);
        nodata_text = findViewById(R.id.noDataTxt);

        button = (FloatingActionButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookEditor.class);
                startActivity(intent);
            }
        });

        db = new DB(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_description = new ArrayList<>();
        book_category = new ArrayList<>();
        book_publisher = new ArrayList<>();
        storeDatatoArrays();

        adapter = new CustomAdapter(MainActivity.this,this, book_id, book_title, book_author, book_description, book_category, book_publisher);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void storeDatatoArrays(){
        Cursor cursor = db.readallData();
        if(cursor.getCount() == 0){
            nodata_image.setVisibility(View.VISIBLE);
            nodata_text.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_description.add(cursor.getString(3));
                book_category.add(cursor.getString(4));
                book_publisher.add(cursor.getString(5));
            }
            nodata_image.setVisibility(View.GONE);
            nodata_text.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.deletethelibrary:

                confirmDialog();

                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all data?");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Are you sure to delete all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB db = new DB(MainActivity.this);
                db.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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