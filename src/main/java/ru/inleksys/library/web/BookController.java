package ru.inleksys.library.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.inleksys.library.model.Book;
import ru.inleksys.library.model.User;
import ru.inleksys.library.repository.BookRepository;

@Controller
public class BookController {

    private final BookRepository br;

    @Autowired
    public BookController(BookRepository br) {
        this.br = br;
    }

    @GetMapping("/books_list")
    public String getBooks(Model model,
                            @RequestParam(defaultValue="0") int from,
                            @RequestParam(defaultValue="5") int count) {
        model.addAttribute("book_list", br.getBooks(from, count));
        return "books_list";

    }

    @GetMapping("/books")
    public String showBooks() {
        return "books";
    }

    //TODO: understand how to transfer username
    @PostMapping("/books/take")
    public String takeBookByUser(Book which_book, User whoTake) {
        br.takeBook(which_book, whoTake);
        return "redirect:/books";
    }

    //TODO: understand how to transfer username
    @PostMapping("/books/return")
    public String returnBookByUser(Book which_book) {
        br.returnBook(which_book);
        return "redirect:/books";
    }

    @GetMapping("/books/new")
    public String initCreationForm(Model model) {
        model.addAttribute(new Book());
        return "book_form";
    }

    @PostMapping("/books/new")
    public String processCreationForm(Book new_book, Errors errors) {
        if (errors.hasErrors()) return "book_form";
        br.addBook(new_book);
        return "redirect:/books";
    }

    @GetMapping("/books/delete")
    public String deleteBook(Book del_book) {
        br.delBook(del_book);
        return "redirect:/books";
    }

    @GetMapping("/books/edit")
    public String initUpdateBookForm(Book book, Model model) {
        model.addAttribute(br.findBookByISN(book));
        return "book_form";
    }

    @PostMapping("/books/edit")
    public String processUpdateBookForm(Book edit_book, String last_isn, Errors errors) {
        if (errors.hasErrors()) return "book_form";
        else {
            br.editBook(edit_book, last_isn);
            return "redirect:/books";
        }
    }

}
