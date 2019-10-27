package ru.inleksys.library.model;

import ru.inleksys.library.Vallidation.Unique;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Unique
public class Book {

    @NotEmpty(message = "Поле ISN не может быть пустым")
    @Size(min = 10, max = 30, message = "Длинна поля ISN от {min} до {max} знаков")
    private String ISN;

    @NotEmpty(message = "Поле Автор не может быть пустым")
    @Size(min = 3, max = 40, message = "Длинна поля Aвтор от {min} до {max} знаков")
    private String Author;

    @NotEmpty(message = "Поле Название не может быть пустым")
    @Size(min = 3, message = "Длинна поля Название от {min} знаков")
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
