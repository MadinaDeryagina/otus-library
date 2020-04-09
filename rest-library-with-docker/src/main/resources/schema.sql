drop table if exists genres;
create table genres
(
    id   serial primary key ,
    name varchar(255)
);
drop table if exists books;
create table books
(
    id serial primary key ,
    title varchar(255)
);
drop table if exists authors;
create table authors
(
    id   serial primary key ,
    name varchar(255)
);
drop table if exists books_authors_correlation;
create table books_authors_correlation
(
    book_id   serial not null,
    author_id serial not null,
    foreign key (book_id) references books (id) ON DELETE CASCADE,
    foreign key (author_id) references authors (id) ON DELETE CASCADE
);
drop table if exists books_genres_correlation;
create table books_genres_correlation
(
    book_id   serial not null,
    genre_id serial not null,
    foreign key (book_id) references books (id) ON DELETE CASCADE,
    foreign key (genre_id) references genres (id) ON DELETE CASCADE
);
drop table if exists comments;
create table comments
(
    id serial primary key ,
    book_id serial not null,
    text varchar(2000),
    foreign key (book_id) references books (id) ON DELETE CASCADE
);