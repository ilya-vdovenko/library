CREATE TABLE users (
    username VARCHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE books (
    isn     VARCHAR(30)  NOT NULL PRIMARY KEY,
    author  VARCHAR(40)  NOT NULL,
    title   VARCHAR(255) NOT NULL,
    user    VARCHAR(30)
);

ALTER TABLE books ADD CONSTRAINT fk_books_users FOREIGN KEY (user) REFERENCES users (username);