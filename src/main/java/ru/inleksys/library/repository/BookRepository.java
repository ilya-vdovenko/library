package ru.inleksys.library.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.inleksys.library.model.Book;
import ru.inleksys.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbctemplate;
   // private final int count_of_rows = 5;

    private static final class BookRowMapper implements RowMapper<Book> {
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getString("isn"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("user"));
        }
    }

    @Autowired
    BookRepository(JdbcTemplate jdbc) {
        //jdbc.setMaxRows(count_of_rows);
        this.jdbctemplate = jdbc;

    }

    public List<Book> getBooks() {
        return jdbctemplate.query("Select * from books order by author asc", new BookRowMapper());
    }

    //TODO: understand how to transfer username
    public void takeBook(Book which_book, User whoTake) {
        jdbctemplate.update("Update books set user = ? where isn = ?", whoTake.getUsername(), which_book.getISN());
    }


    public void returnBook(Book which_book) {
        jdbctemplate.update("Update books set user = null where isn = ?", which_book.getISN());
    }

    public void addBook(Book new_book) {
        jdbctemplate.update("Insert into books values (?, ?, ?, null)",
                new_book.getISN(),
                new_book.getAuthor(),
                new_book.getTitle());
    }

    //TODO: may be parametr must be object?
    public void delBook(String isn) {
        jdbctemplate.update("Delete from books where isn = ?", isn);
    }

}
