package otus.deryagina.spring.libraryjdbc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.dao.AuthorDao;
import otus.deryagina.spring.libraryjdbc.dao.BookDao;
import otus.deryagina.spring.libraryjdbc.dao.GenreDao;
import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.domain.Genre;
import otus.deryagina.spring.libraryjdbc.dto.AuthorDTO;
import otus.deryagina.spring.libraryjdbc.dto.BookDTO;
import otus.deryagina.spring.libraryjdbc.dto.GenreDTO;
import otus.deryagina.spring.libraryjdbc.mapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final ModelMapper modelMapper;


    @Override
    public long addAsNewBook(BookDTO bookDTO) {
        List<Author> authors = prepareAuthorForNewBook(bookDTO);
        List<Genre> genres = prepareGenresForNewBook(bookDTO);
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthors(authors);
        book.setGenres(genres);
        return bookDao.insert(book);
    }

    private List<Genre> prepareGenresForNewBook(BookDTO bookDTO) {
        List<GenreDTO> genreDTOS = bookDTO.getGenreDTOS();
        List<String> genresNames = genreDTOS.stream()
                .map(GenreDTO::getName).collect(Collectors.toList());
        // check if there are genres,which are already in the library
        List<Genre> genresFromDb = genreDao.findGenresByNames(genresNames);
        List<String> genresNamesFromDb = genresFromDb.stream().
                map(Genre::getName).collect(Collectors.toList());
        //get existing genres if they are, create new for those who are not
        List<Genre> genresForNewBook = new ArrayList<>(genresFromDb);
        // if there are genres who are not in db
        if (genresNames.size() > genresNamesFromDb.size()) {
            List<Genre> genresToSave = genreDTOS.stream().filter(x -> !genresNamesFromDb.contains(x.getName()))
                    .map(modelMapper::dtoToEntity).collect(Collectors.toList());
            for (Genre genre :
                    genresToSave) {
                long idFromDb = genreDao.insert(genre);
                genre.setId(idFromDb);
                genresForNewBook.add(genre);
            }
        }
        return genresForNewBook;
    }

    private List<Author> prepareAuthorForNewBook(BookDTO bookDTO) {
        List<AuthorDTO> authorDTOS = bookDTO.getAuthorDTOS();
        List<String> authorsNames = authorDTOS.stream()
                .map(AuthorDTO::getFullName).collect(Collectors.toList());
        // check if there are authors,who are already in the library
        List<Author> authorsFromDb = authorDao.findAuthorsByNames(authorsNames);
        //get existing authors if they are, create new for those who are not
        List<Author> authorsForNewBook = new ArrayList<>(authorsFromDb);
        // if there are authors who are not in db
        if (authorsNames.size() > authorsFromDb.size()) {
            List<String> authorsNamesFromDb = authorsFromDb.stream().
                    map(Author::getFullName).collect(Collectors.toList());
            List<Author> authorsToSave = authorDTOS.stream().filter(x -> !authorsNamesFromDb.contains(x.getFullName()))
                    .map(modelMapper::dtoToEntity).collect(Collectors.toList());
            for (Author author :
                    authorsToSave) {
                long idFromDb = authorDao.insert(author);
                author.setId(idFromDb);
                authorsForNewBook.add(author);
            }
        }
        return authorsForNewBook;
    }

    @Override
    public List<BookDTO> findAllBooks() {
        List<BookDTO> resultList = new ArrayList<>();
        List<Book> listOfBooks = bookDao.findAll();
        if (listOfBooks == null || listOfBooks.isEmpty()) {
            return resultList;
        }
        resultList = modelMapper.entityToDto(listOfBooks);
        return resultList;
    }

    @Override
    public BookDTO findBookById(long id) {
        Book book = bookDao.findById(id);
        if (book == null) {
            return null;
        }
        return modelMapper.entityToDto(book);
    }

    @Override
    public List<BookDTO> findBooksByTitle(String title) {
        List<Book> books = bookDao.findBooksByTitle(title);
        if (books.isEmpty()) {
            return new ArrayList<>();
        } else {
            return modelMapper.entityToDto(books);
        }
    }

    @Override
    public boolean updateBook(long id, BookDTO targetInfo) {
        //bookToUpdate id is id from db
        Book bookToUpdate = bookDao.findById(id);
        //compare bookToUpdate with targetInfo to understand what to update
        boolean isUpdated=false;
        if(!bookToUpdate.getTitle().equalsIgnoreCase(targetInfo.getTitle())){
            bookDao.updateBookTitle(bookToUpdate.getId(),targetInfo.getTitle());
            isUpdated = true;
        }
        boolean isAuthorUpdated =isAuthorUpdated(bookToUpdate, targetInfo);
        isUpdated = isUpdated||isAuthorUpdated;
        boolean isGenreUpdated =isGenreUpdated(bookToUpdate,targetInfo);
        isUpdated = isUpdated||isGenreUpdated;
        return isUpdated;
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }

    private boolean isAuthorUpdated(Book bookToUpdate, BookDTO targetInfo) {
        boolean isUpdated = false;
        List<String> targetInfoAuthorsNames = targetInfo.getAuthorDTOS().stream()
                .map(AuthorDTO::getFullName).collect(Collectors.toList());
        List<String> currentAuthorsNames = bookToUpdate.getAuthors().stream()
                .map(Author::getFullName).collect(Collectors.toList());

        List<String> authorsNamesToAddToBook = targetInfoAuthorsNames.stream()
                .filter(x-> !currentAuthorsNames.contains(x)).collect(Collectors.toList());

        HashSet<String> authorNamesNotInDb = new HashSet<>(authorsNamesToAddToBook);
        if(!authorsNamesToAddToBook.isEmpty()){
            List<Author> authorsFromDb = authorDao.findAuthorsByNames(authorsNamesToAddToBook);
          for(Author currentAuthorFromDB : authorsFromDb){
              bookDao.addAuthorForBook(bookToUpdate.getId(),currentAuthorFromDB.getId());
              authorNamesNotInDb.remove(currentAuthorFromDB.getFullName());
              isUpdated=true;
          }
          if(!authorNamesNotInDb.isEmpty()){
              for (String currentName : authorNamesNotInDb){
                  Author author= new Author();
                  author.setFullName(currentName);
                  long newAuthorId = authorDao.insert(author);
                  bookDao.addAuthorForBook(bookToUpdate.getId(),newAuthorId);
                  isUpdated=true;
              }
          }
        }
        List<String> authorNamesToDeleteFromBook = bookToUpdate.getAuthors()
                .stream().map(Author::getFullName)
                .filter(x -> !targetInfoAuthorsNames.contains(x))
                .collect(Collectors.toList());
        if(!authorNamesToDeleteFromBook.isEmpty()){
            List<Author> authorToDeleteFromBook = authorDao.findAuthorsByNames(authorNamesToDeleteFromBook);
            for(Author currentAuthor : authorToDeleteFromBook){
                bookDao.deleteAuthorFromBook(bookToUpdate.getId(),currentAuthor.getId());
                isUpdated=true;
            }
        }
        return isUpdated;
    }
    private boolean isGenreUpdated(Book bookToUpdate, BookDTO targetInfo) {
        boolean isUpdated = false;
        List<String> targetInfoGenresNames = targetInfo.getGenreDTOS().stream()
                .map(GenreDTO::getName).collect(Collectors.toList());
        List<String> currentGenresNames = bookToUpdate.getGenres().stream()
                .map(Genre::getName).collect(Collectors.toList());

        List<String> genresNamesToAddToBook = targetInfoGenresNames.stream()
                .filter(x-> !currentGenresNames.contains(x)).collect(Collectors.toList());
        HashSet<String> genreNamesNotInDb = new HashSet<>(genresNamesToAddToBook);
        if(!genresNamesToAddToBook.isEmpty()){
            List<Genre> genresFromDb = genreDao.findGenresByNames(genresNamesToAddToBook);
            for(Genre currentGenreFromDB : genresFromDb){
                bookDao.addGenreForBook(bookToUpdate.getId(),currentGenreFromDB.getId());
                genreNamesNotInDb.remove(currentGenreFromDB.getName());
                isUpdated=true;
            }
            if(!genreNamesNotInDb.isEmpty()){
                for (String currentName : genreNamesNotInDb){
                    Genre genre= new Genre();
                    genre.setName(currentName);
                    long newGenreId = genreDao.insert(genre);
                    bookDao.addGenreForBook(bookToUpdate.getId(),newGenreId);
                    isUpdated=true;
                }
            }
        }
        List<String> genreNamesToDeleteFromBook = bookToUpdate.getGenres()
                .stream().map(Genre::getName)
                .filter(x->!targetInfoGenresNames.contains(x))
                .collect(Collectors.toList());
        if(!genreNamesToDeleteFromBook.isEmpty()){
            List<Genre> genreToDeleteFromBook = genreDao.findGenresByNames(genreNamesToDeleteFromBook);
            for(Genre currentGenre : genreToDeleteFromBook){
                bookDao.deleteGenreFromBook(bookToUpdate.getId(),currentGenre.getId());
                isUpdated=true;
            }
        }
        return isUpdated;
    }

}
