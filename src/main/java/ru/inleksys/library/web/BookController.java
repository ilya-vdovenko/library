package ru.inleksys.library.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.inleksys.library.model.Book;
import ru.inleksys.library.model.User;
import ru.inleksys.library.repository.BookRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

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
                            @RequestParam(defaultValue="5") int count,
                            @RequestParam(defaultValue="author") String by,
                            @RequestParam(defaultValue="asc") String order) {
        model.addAttribute("book_list", br.getBooks(from, count, by, order));
        return "books_list";

    }

    @GetMapping("/books")
    public String showBooks() {
        return "books";
    }

    @GetMapping("/books/take")
    @ResponseStatus(OK)
    public void takeBookByUser( @RequestParam String isn,
                                @RequestParam String whoTake) {
        br.takeBook(isn, whoTake);
    }

    @GetMapping("/books/return")
    @ResponseStatus(OK)
    public void returnBookByUser(@RequestParam String isn) {
        br.returnBook(isn);
    }

    @GetMapping("/books/new")
    public String initCreationForm(Model model) {
        model.addAttribute("book", new Book());
        return "book_form";
    }

    @PostMapping("/books/new")
    public String processCreationForm(@Valid Book new_book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book",new_book);
            return "book_form";
        }
        br.addBook(new_book);
        return "redirect:/books";
    }

    @GetMapping("/books/delete")
    public String deleteBook(Model model,
                             @RequestParam String isn,
                             @RequestParam int from,
                             @RequestParam String by,
                             @RequestParam String order) {
        br.delBook(isn);
        return getBooks(model, from, 1, by, order);
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
