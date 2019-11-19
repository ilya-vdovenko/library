package ru.inleksys.library.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.inleksys.library.model.Book;
import ru.inleksys.library.repository.BookRepository;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class BookController {

    private final BookRepository br;
    private String lastISN = "";

    @Autowired
    public BookController(BookRepository br) {
        this.br = br;
    }

    @InitBinder("book")
    public void initBookBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new BookValidator(br, lastISN));
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
    public String initUpdateBookForm(@RequestParam String isn, Model model) {
        Book edit_book = br.findBookByISN(isn);
        lastISN = isn;
        model.addAttribute(edit_book);
        return "book_form";
    }

    @PostMapping("/books/edit")
    public String processUpdateBookForm(@Valid Book edit_book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book",edit_book);
            return "book_form";
        }
        br.editBook(edit_book, lastISN);
        return "redirect:/books";
    }
}
