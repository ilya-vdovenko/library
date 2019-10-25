package ru.inleksys.library.Vallidation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.inleksys.library.model.Book;
import ru.inleksys.library.repository.BookRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private BookRepository br;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        List<Book> list = br.getAllBooks();
        String isn = ((Book) value).getISN();
        for(Book book: list) {
            if(book.getISN().equals(isn))
                return false;
        }
        return true;
    }
}
