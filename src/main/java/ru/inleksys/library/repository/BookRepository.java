package ru.inleksys.library.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.inleksys.library.model.Book;
import ru.inleksys.library.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbctemplate;

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
        this.jdbctemplate = jdbc;

    }

    public Book findBookByISN(Book book) {
        return jdbctemplate.queryForObject("Select * from books where isn = "+book.getISN(), new BookRowMapper());
    }

    public List<Book> getBooks(int from, int count, String by, String order) {
              return jdbctemplate.query("Select * from books order by "+by+" "+order+" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY",
                new BookRowMapper(), from, count);
    }

    public List<Book> getAllBooks() {
        return jdbctemplate.query("Select * from books", new BookRowMapper());
    }

    public void takeBook(String isn, String whoTake) {
        jdbctemplate.update("Update books set user = ? where isn = ?", whoTake, isn);
    }

    public void returnBook(String isn) {
        jdbctemplate.update("Update books set user = null where isn = ?", isn);
    }

    public void addBook(Book new_book) {
        jdbctemplate.update("Insert into books values (?, ?, ?, null)",
                new_book.getISN(),
                new_book.getAuthor(),
                new_book.getTitle());
    }

    public void editBook(Book edit_book, String last_isn) {
        jdbctemplate.update("Update books set isn = ?, author = ?, title = ? where isn ="+last_isn,
                edit_book.getISN(),
                edit_book.getAuthor(),
                edit_book.getTitle());
    }

    public void delBook(String isn) {
        jdbctemplate.update("Delete from books where isn = ?", isn);
    }

}
