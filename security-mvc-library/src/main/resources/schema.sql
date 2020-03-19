drop table if exists users;
create table users
(
    username varchar(50) primary key not null,
    password char(68)                not null,
    enabled  tinyint                 not null
);

drop table if exists authorities;
create table authorities
(
    id   long primary key auto_increment,
    role varchar(50)
);

drop table if exists users_authorities_correlation;
create table users_authorities_correlation
(
    username_id  varchar(50) not null,
    authority_id long        not null,
    foreign key (username_id) references users (username) ON DELETE CASCADE,
    foreign key (authority_id) references authorities (id) ON DELETE CASCADE
);

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
    book_id   long not null,
    author_id long not null,
    foreign key (book_id) references books (id) ON DELETE CASCADE,
    foreign key (author_id) references authors (id) ON DELETE CASCADE
);
drop table if exists books_genres_correlation;
create table books_genres_correlation
(
    book_id  long not null,
    genre_id long not null,
    foreign key (book_id) references books (id) ON DELETE CASCADE,
    foreign key (genre_id) references genres (id) ON DELETE CASCADE
);
drop table if exists comments;
create table comments
(
    id      long primary key auto_increment,
    book_id long not null,
    text    varchar(2000),
    foreign key (book_id) references books (id) ON DELETE CASCADE
);