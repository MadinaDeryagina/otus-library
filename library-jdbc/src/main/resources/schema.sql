drop table if exists genres;
create table genres
(
    id   long primary key auto_increment,
    name varchar(255)
);
drop table if exists books;
create table books
(
    id    long primary key auto_increment,
    title varchar(255)
);
drop table if exists authors;
create table authors
(
    id   long primary key auto_increment,
    name varchar(255)
);
drop table if exists books_authors_correlation;
create table books_authors_correlation
(
    bookId   long not null,
    authorId long not null,
    foreign key (bookId) references books (id),
    foreign key (authorId) references authors (id)
);
drop table if exists books_genres_correlation;
create table books_genres_correlation
(
    bookId   long not null,
    genreId long not null,
    foreign key (bookId) references books (id),
    foreign key (genreId) references genres (id)
);