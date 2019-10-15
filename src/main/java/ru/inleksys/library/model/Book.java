package ru.inleksys.library.model;

public class Book {

    private String ISN;
    private String Author;
    private String Title;
    private User whoTook;

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
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public User getWhoTook() {
        return whoTook;
    }

    public void setWhoTook(User whoTook) {
        this.whoTook = whoTook;
    }
}
