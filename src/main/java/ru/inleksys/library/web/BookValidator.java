package ru.inleksys.library.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.inleksys.library.model.Book;
import ru.inleksys.library.repository.BookRepository;

public class BookValidator implements Validator {

    private BookRepository bookRepo;
    private String lastISN;

    public BookValidator(BookRepository bookRepo, String lastISN) {
        this.bookRepo = bookRepo;
        this.lastISN = lastISN;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Book book = (Book) obj;
        String ISN = book.getISN();
        String Author = book.getAuthor();
        String Title = book.getTitle();
        try {
            bookRepo.findBookByISN(ISN);
            if (!ISN.equals(lastISN)) {
                errors.rejectValue("ISN", "Duplicate",  "Книга с таким ISN уже существует");
            }
        }
        catch (Exception exc) {
        }
        if (ISN.length()<13 | ISN.length()>20) {
            errors.rejectValue("ISN", "size", "Длинна поля ISN от 13 до 20 знаков");
        }
        if (Author.length()<5 | Author.length()>40) {
            errors.rejectValue("author", "size", "Длинна поля Автор от 5 до 40 знаков");
        }
        if (Title.length()<3 | Title.length()>255) {
            errors.rejectValue("title", "size", "Длинна поля Название от 3 до 255 знаков");
        }
    }
}
