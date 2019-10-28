package ru.inleksys.library.model;

public class Book {

    private String ISN;
    private String Author;
    private String Title;
    private String whoTook;

    public Book() {}

    public Book(String isn, String author, String title, String whoTook) {
        this.ISN = isn;
        this.Author = author;
        this.Title = title;
        this.whoTook = whoTook;
    }

    public String getISN() {
        return ISN;
    }

    public void setISN(String ISN) {
        this.ISN = ISN;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getWhoTook() {
        return whoTook;
    }

    public void setWhoTook(String whoTook) {
        this.whoTook = whoTook;
    }
}
