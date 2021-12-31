package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public static int id=1;
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table BookListNew(bookId NUMBER,bookName TEXT,bookAuthorName TEXT,genre TEXT,bookType TEXT,launchDate TEXT,agePrefer TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean insertData(String bookName,String bookAuthorName,String genre,String bookType,String launchDate,String agePrefer){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("bookId",id);
        id=id+1;
        contentValues.put("bookName",bookName);
        contentValues.put("bookAuthorName",bookAuthorName);
        contentValues.put("genre",genre);
        contentValues.put("bookType",bookType);
        contentValues.put("launchDate",launchDate);
        contentValues.put("agePrefer",agePrefer);
        long result=DB.insert("BookListNew",null,contentValues);
        return result != -1;
    }
    public Cursor getData()
    {
        SQLiteDatabase Db=this.getWritableDatabase();
        return Db.rawQuery("Select * from BookListNew",null);
    }
    public Cursor getDataBookName(int id)
    {
        SQLiteDatabase Db=this.getWritableDatabase();

        return Db.rawQuery("select * from BookListNew Where bookId=?",new String[]{String.valueOf(id)}); }

    public Boolean updateData(String bookName,String bookAuthorName,String genre,String bookType,String launchDate,String agePrefer){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
//        contentValues.put("bookName",bookName);
        contentValues.put("bookAuthorName",bookAuthorName);
        contentValues.put("genre",genre);
        contentValues.put("bookType",bookType);
        contentValues.put("launchDate",launchDate);
        contentValues.put("agePrefer",agePrefer);
        long result=DB.update("BookListNew",contentValues, "bookName=?", new String[]{bookName});
        return result != -1;
    }
}
