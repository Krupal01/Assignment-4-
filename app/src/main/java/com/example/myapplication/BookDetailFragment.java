package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    public BookItem bookItem;
    public Database db;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book Detail");


        Bundle bundle = getArguments();
        if (!bundle.isEmpty()) {
            bookItem = (BookItem) bundle.getSerializable("bookitem");
            bundle.clear();
        }

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.book_detail_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        db = new Database(getContext(),"BookList",null,1);
        if(item.getItemId() == R.id.edit){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_edit_dialog, null);
            builder.setView(dialogView);
            builder.setTitle(bookItem.bookname);
            builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    boolean Update = db.updateData(bookItem.bookname,
                            EditDetail(dialogView).authorname,
                            EditDetail(dialogView).generation,
                            EditDetail(dialogView).fiction,
                            EditDetail(dialogView).date,
                            EditDetail(dialogView).ageLimit);
                    if (Update){
                        Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();
                    }

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainActivity,new BooklistFragment())
                            .addToBackStack(null)
                            .commit();

                }
            });
            builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    private BookItem EditDetail(View view) {
        BookItem newDetail = new BookItem();

        EditText editauthorname = view.findViewById(R.id.editAuthorName);
        RadioGroup editrbfiction = view.findViewById(R.id.editradioGrp);
        CheckBox editch3 = view.findViewById(R.id.editcheck3);
        CheckBox editch12 = view.findViewById(R.id.editcheck12);
        CheckBox editch18 = view.findViewById(R.id.editcheck18);
        CheckBox editch32 = view.findViewById(R.id.editcheck32);
        CheckBox editch52 = view.findViewById(R.id.editcheck52);
        EditText editdatePicker = view.findViewById(R.id.editdatePicker);
        EditText editgenration = view.findViewById(R.id.editGenration);

        //radiobutton
        editrbfiction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);
                newDetail.setFiction(rb.getText().toString());
            }
        });

        //checkBox
        String editlimit = "";
        if (editch3.isChecked()){editlimit+=editch3.getText().toString();}
        if (editch12.isChecked()){editlimit+=editch12.getText().toString();}
        if (editch18.isChecked()){editlimit+=editch18.getText().toString();}
        if (editch32.isChecked()){editlimit+=editch32.getText().toString();}
        if (editch52.isChecked()){editlimit+=editch52.getText().toString();}

        newDetail.setGeneration(editgenration.getText().toString());
        newDetail.setAuthorname(editauthorname.getText().toString());
        newDetail.setDate(editdatePicker.getText().toString());
        newDetail.setAgeLimit(editlimit);

        return newDetail;
    }
}