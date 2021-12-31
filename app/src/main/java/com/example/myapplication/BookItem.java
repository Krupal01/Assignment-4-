package com.example.myapplication;

import java.io.Serializable;
import java.util.Comparator;

public class BookItem implements Serializable {
    String bookname,authorname,generation,fiction,date,ageLimit;

    public BookItem() {
    }

    public BookItem(String bookname, String authorname, String generation, String fiction, String date, String ageLimit) {
        this.bookname = bookname;
        this.authorname = authorname;
        this.generation = generation;
        this.fiction = fiction;
        this.date = date;
        this.ageLimit = ageLimit;
    }
    public BookItem(int id ,String bookname, String authorname, String generation, String fiction, String date, String ageLimit) {
        this.bookname = bookname;
        this.authorname = authorname;
        this.generation = generation;
        this.fiction = fiction;
        this.date = date;
        this.ageLimit = ageLimit;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthorname() {
        return authorname;
    }

    public String getGeneration() {
        return generation;
    }

    public String getFiction() {
        return fiction;
    }

    public String getDate() {
        return date;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public void setFiction(String fiction) {
        this.fiction = fiction;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }

    public static Comparator<BookItem> BookNameComparator = new Comparator<BookItem>() {

        public int compare(BookItem b1, BookItem b2)
        {
            String BookName1
                    = b1.getBookname().toUpperCase();
            String BookName2
                    = b2.getBookname().toUpperCase();
            return BookName1.compareTo(
                    BookName2);
        }
    };

    public static Comparator<BookItem> AuthorNameComparator = new Comparator<BookItem>() {

        public int compare(BookItem b1, BookItem b2)
        {
            String AuthorName1
                    = b1.getAuthorname().toUpperCase();
            String AuthorName2
                    = b2.getAuthorname().toUpperCase();
            return AuthorName1.compareTo(
                    AuthorName2);
        }
    };
}
