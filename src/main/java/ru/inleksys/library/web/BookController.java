package ru.inleksys.library.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inleksys.library.repository.BookRepository;

@RestController
public class BookController {

    private final BookRepository br;

    @Autowired
    public BookController(BookRepository br) {
        this.br = br;
    }

    @GetMapping("/book")
    public String getBooks() {

        return "header";
    }
}
