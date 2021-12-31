package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<BookItem> Books = new ArrayList<>();


    public void setBooks(ArrayList<BookItem> books) {
        this.Books = books;
    }

    interface ClickListener{
        void ClickListener(BookItem bookItem);
    }

    private ClickListener listener;

    public BookAdapter(ClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_item,parent,false);
        BookViewHolder viewHolder = new BookViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookItem bookItem = Books.get(position);
        holder.bookname.setText(bookItem.getBookname());
        holder.authorname.setText(bookItem.getAuthorname());
        holder.generation.setText(bookItem.getGeneration());

        holder.itemView.setOnClickListener(view -> listener.ClickListener(bookItem));
    }

    @Override
    public int getItemCount() {
        return Books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView bookname;
        private final TextView authorname;
        private final TextView generation;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.bookNameItem);
            authorname = itemView.findViewById(R.id.authorNameItem);
            generation = itemView.findViewById(R.id.generationItem);
        }
    }
}
