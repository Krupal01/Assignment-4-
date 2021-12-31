package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddbookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddbookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddbookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddbookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddbookFragment newInstance(String param1, String param2) {
        AddbookFragment fragment = new AddbookFragment();
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
        return inflater.inflate(R.layout.fragment_addbook, container, false);
    }

    public EditText bookname,authorname;
    public Spinner genration;
    public Button datePicker;
    public RadioGroup rbfiction;
    public CheckBox ch3,ch12,ch18,ch32,ch52;
    public Button AddBook;
    private BooklistFragment booklistFragment = new BooklistFragment();
    String[] genArray = { "1st gen", "2nd gen", "3rd gen", "4th gen", "5th gen"};
    String gen,type,date1,limit = new String();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Book");

        AddBook = view.findViewById(R.id.AddBook);
        bookname = view.findViewById(R.id.BookName);
        authorname = view.findViewById(R.id.AuthorName);
        rbfiction = view.findViewById(R.id.radioGrp);
        ch3 = view.findViewById(R.id.check3);
        ch12 = view.findViewById(R.id.check12);
        ch18 = view.findViewById(R.id.check18);
        ch32 = view.findViewById(R.id.check32);
        ch52 = view.findViewById(R.id.check52);
        datePicker = view.findViewById(R.id.datePicker);

        //spinner
        genration = view.findViewById(R.id.Genration);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,genArray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genration.setAdapter(aa);

        genration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gen = genArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //radiobutton
        rbfiction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);
                type = rb.getText().toString();
            }
        });

         //datePicker
        Calendar myCalendar= Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                datePicker.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });



        AddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checkbox
                if (ch3.isChecked()){limit+=ch3.getText().toString();}
                if (ch12.isChecked()){limit+=ch12.getText().toString();}
                if (ch18.isChecked()){limit+=ch18.getText().toString();}
                if (ch32.isChecked()){limit+=ch32.getText().toString();}
                if (ch52.isChecked()){limit+=ch52.getText().toString();}

                date1 = datePicker.getText().toString();

                BookItem bookItem = new BookItem(
                        bookname.getText().toString(),
                        authorname.getText().toString(),
                        gen,
                        type,
                        date1,
                        limit
                        );

                Bundle bundle = new Bundle();
                bundle.putSerializable("Add",bookItem);
                booklistFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainActivity,booklistFragment)
                        .commit();

                Toast.makeText(getContext(), "Added sucessfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}