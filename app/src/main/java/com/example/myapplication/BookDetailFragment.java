package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetailFragment newInstance(String param1, String param2) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book Detail");

        Bundle bundle = getArguments();
        BookItem bookItem = (BookItem) bundle.getSerializable("bookitem");

        TextView bookname = view.findViewById(R.id.tvbookname);
        TextView authorname = view.findViewById(R.id.tvauthorname);
        TextView generation = view.findViewById(R.id.tvgeneration);
        TextView date = view.findViewById(R.id.tvDate);
        TextView fiction = view.findViewById(R.id.tvfiction);
        TextView agelimit = view.findViewById(R.id.tvAgelimit);

        bookname.setText(bookname.getText() +" "+ bookItem.getBookname());
        authorname.setText(authorname.getText()+" "+bookItem.getAuthorname());
        generation.setText(generation.getText()+" "+bookItem.getGeneration());
        date.setText(date.getText()+" "+bookItem.getDate());
        fiction.setText(fiction.getText()+" "+bookItem.getFiction());
        agelimit.setText(agelimit.getText()+" "+bookItem.getAgeLimit());
    }
}