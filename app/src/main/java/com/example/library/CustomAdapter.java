package com.example.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.CollationElementIterator;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList book_id, book_title, book_author, book_description, book_category, book_publisher;

    Animation animation;

    CustomAdapter(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author, ArrayList book_description,
                  ArrayList book_category, ArrayList book_publisher){

        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_description = book_description;
        this.book_category = book_category;
        this.book_publisher = book_publisher;

    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_category_txt.setText(String.valueOf(book_category.get(position)));
        holder.book_publisher_txt.setText(String.valueOf(book_publisher.get(position)));
        holder.book_description_txt.setText(String.valueOf(book_description.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateBook.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("category", String.valueOf(book_category.get(position)));
                intent.putExtra("publisher", String.valueOf(book_publisher.get(position)));
                intent.putExtra("description", String.valueOf(book_description.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView book_id_txt, book_title_txt, book_author_txt, book_category_txt,  book_publisher_txt, book_description_txt;
        LinearLayout mainLayout;
        public MyViewHolder(View view) {
            super(view);
            book_title_txt = view.findViewById(R.id.book_title_txt);
            book_author_txt = view.findViewById(R.id.book_author_text);
            book_category_txt = view.findViewById(R.id.book_category_txt);
            book_publisher_txt = view.findViewById(R.id.book_publisher_txt);
            book_description_txt = view.findViewById(R.id.book_description_txt);
            mainLayout = view.findViewById(R.id.mainLayout);
            animation = AnimationUtils.loadAnimation(context, R.anim.anima);
            mainLayout.setAnimation(animation);
        }
    }
}
