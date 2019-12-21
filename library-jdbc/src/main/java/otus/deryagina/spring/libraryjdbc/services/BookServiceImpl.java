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
        List<String> authorsNamesFromDb = authorsFromDb.stream().
                map(Author::getFullName).collect(Collectors.toList());
        //get existing authors if they are, create new for those who are not
        List<Author> authorsForNewBook = new ArrayList<>(authorsFromDb);
        // if there are authors who are not in db
        if (authorsNames.size() > authorsNamesFromDb.size()) {
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
    public boolean updateBook(BookDTO bookToUpdate, BookDTO targetInfo) {
        return false;
    }


}
