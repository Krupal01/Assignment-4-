package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

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
    public ArrayList<BookItem> booklist = new ArrayList<>() ;
    public BookAdapter adapter;
    public Database db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createBookDataList();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        db = new Database(getContext(),"BookList",null,1);
        return inflater.inflate(R.layout.fragment_booklist, container, false);
    }



    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book List");



        recyclerView = view.findViewById(R.id.BookRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new BookAdapter(this);
        adapter.setBooks(booklist);
        recyclerView.setAdapter(adapter);


    }
    public void createBookDataList() {
        db = new Database(getContext(),"BookList",null,1);
        Cursor getData = db.getData();
        if (getData.getCount() == 0) {
            Toast.makeText(getContext(), "No Books Right Now", Toast.LENGTH_SHORT).show();

        }
        while (getData.moveToNext()) {
            booklist.add(new BookItem(getData.getInt(0), getData.getString(1), getData.getString(2), getData.getString(3), getData.getString(4), getData.getString(5), getData.getString(6)));
        }
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.book_list_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }

        });

    }
    private void filter(String text) {
        ArrayList<BookItem> filteredlist = new ArrayList<>();

        for ( BookItem item : booklist) {
            if (item.getBookname().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
            else if (item.getAuthorname().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setBooks(filteredlist);
            adapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        switch (item.getItemId()){
            case R.id.BookNameSort:
                Collections.sort(booklist,BookItem.BookNameComparator);
                adapter.notifyDataSetChanged();
                break;

            case R.id.AuthorNameSort:
                Collections.sort(booklist,BookItem.AuthorNameComparator);
                adapter.notifyDataSetChanged();
                break;

            case R.id.GenreFilter:
                View view = inflater.inflate(R.layout.custom_genre_selection,null);
                builder.setView(view);
                CheckBox ch1gen = view.findViewById(R.id.Filtercheck1);
                CheckBox ch2gen = view.findViewById(R.id.Filtercheck2);
                CheckBox ch3gen = view.findViewById(R.id.Filtercheck3);
                CheckBox ch4gen = view.findViewById(R.id.Filtercheck4);
                CheckBox ch5gen = view.findViewById(R.id.Filtercheck5);

                ArrayList<BookItem> newFilter = new ArrayList<>();
                builder.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ch1gen.isChecked()){
                            for (BookItem b : booklist) {
                                if (b.getGenre().contains("Action and adventure")&& b.getGenre()!=null ){ newFilter.add(b); }
                            }
                        }
                        if (ch2gen.isChecked()){
                            for (BookItem b : booklist) {
                                if (b.getGenre().contains("Crime") && b.getGenre()!=null){ newFilter.add(b); }
                            }
                        }
                        if (ch3gen.isChecked()){
                            for (BookItem b : booklist) {
                                if (b.getGenre().contains("Drama") && b.getGenre()!=null){ newFilter.add(b); }
                            }
                        }
                        if (ch4gen.isChecked()){
                            for (BookItem b : booklist) {
                                if (b.getGenre().contains("Historical") && b.getGenre()!=null){ newFilter.add(b); }
                            }
                        }
                        if (ch5gen.isChecked()){
                            for (BookItem b : booklist) {
                                if (b.getGenre().contains("Horror") && b.getGenre()!=null){ newFilter.add(b); }
                            }
                        }
                        if ((!ch1gen.isChecked())&&(!ch2gen.isChecked())&&(!ch3gen.isChecked())&&(!ch4gen.isChecked())&&(!ch5gen.isChecked()))
                        {
                            for (BookItem b : booklist) {
                                 newFilter.add(b);
                            }
                        }
                        adapter.setBooks(newFilter);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.TypeFilter:
                View view1 = inflater.inflate(R.layout.custom_type_selection,null);
                builder.setView(view1);
                builder.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    ArrayList<BookItem> newFilter1 = new ArrayList<>();
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CheckBox chFiction = view1.findViewById(R.id.FilterFiction);
                        CheckBox chNonFiction = view1.findViewById(R.id.FilterNonFiction);
                        if(chFiction.isChecked()){
                            for (BookItem b : booklist) {
                                if (b.getFiction().equals("fiction") && b.getFiction()!=null){ newFilter1.add(b); }
                            }
                        }
                        if(chNonFiction.isChecked()){
                            for (BookItem b : booklist) {
                                if (b.getFiction().equals("non-fiction") && b.getFiction()!=null){ newFilter1.add(b); }
                            }
                        }
                        if ((!chFiction.isChecked())&&(!chNonFiction.isChecked())){
                            for (BookItem b : booklist) {
                                newFilter1.add(b);
                            }
                        }
                        adapter.setBooks(newFilter1);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog1 = builder.create();
                dialog1.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}