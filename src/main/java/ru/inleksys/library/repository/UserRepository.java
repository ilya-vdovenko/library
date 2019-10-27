package ru.inleksys.library.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.inleksys.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbctemplate;
    private final BCryptPasswordEncoder BCPE;

    private static final class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getString("username"),
                    rs.getString("password"));
        }
    }

    @Autowired
    UserRepository(JdbcTemplate jdbc) {
        this.jdbctemplate = jdbc;
        BCPE = new BCryptPasswordEncoder();
    }

    public User findUserByName(String name) {
        return jdbctemplate.queryForObject("Select * from users where username = ?", new UserRowMapper(), name);
    }

    public List<User> getAllUsers() {
        return jdbctemplate.query("Select * from users order by username asc", new UserRowMapper());
    }

    public void addUser(User new_user) {
        jdbctemplate.update("Insert into users values (?, ?)",
                new_user.getUsername(),
                BCPE.encode(new_user.getPassword()));
    }

    public void editUser(User edit_user, String lastName) {
        jdbctemplate.update("Update users set username = ?, password = ? where username = ?",
                edit_user.getUsername(),
                BCPE.encode(edit_user.getPassword()),
                lastName);
    }

    public void delUser(String name) {
        jdbctemplate.update("Delete from users where username = ?", name);
    }

}
