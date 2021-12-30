package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooklistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooklistFragment extends Fragment implements BookAdapter.ClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BooklistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BooklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BooklistFragment newInstance(String param1, String param2) {
        BooklistFragment fragment = new BooklistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    public ArrayList<BookItem> booklist=new ArrayList<>();
    public BookAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        booklist.add(new BookItem("d","D","D","D","D","D"));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booklist, container, false);
    }


    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book List");

        try {
            Bundle bundle = getArguments();
            if(!bundle.isEmpty()) {
                BookItem bookItem = (BookItem) bundle.getSerializable("Add");
                booklist.add(bookItem);
            }
            bundle.clear();
        }
        catch (Exception ignored){ }



        recyclerView = view.findViewById(R.id.BookRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new BookAdapter(this);
        adapter.setBooks(booklist);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void ClickListener(BookItem bookItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookitem",bookItem);
        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        bookDetailFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainActivity,bookDetailFragment)
                .addToBackStack(null)
                .commit();
    }

}