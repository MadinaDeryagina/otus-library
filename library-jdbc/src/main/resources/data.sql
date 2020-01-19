insert into GENRES(id, name)
values (1, 'Poetry');
insert into GENRES(id, name)
values (2, 'Drama');
insert into GENRES(id, name)
values (3, 'Prose');
insert into GENRES(id, name)
values (4, 'Nonfiction');
insert into GENRES(id, name)
values (5, 'Media');

insert into BOOKS(id, title)
values ( 1, 'Hamlet');
insert into AUTHORS(id, name)
values  (1, 'William Shakespeare');
insert into BOOKS_AUTHORS_CORRELATION
values ( 1, 1 );
insert into BOOKS_GENRES_CORRELATION
values ( 1, 2 );
insert into BOOKS_GENRES_CORRELATION
values ( 1,3 );