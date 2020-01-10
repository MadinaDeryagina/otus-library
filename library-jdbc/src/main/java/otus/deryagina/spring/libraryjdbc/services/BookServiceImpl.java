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
        List<Author> authors = getAndInsertAuthors(bookDTO.getAuthorDTOS());
        List<Genre> genres = getAndInsertGenres(bookDTO.getGenreDTOS());
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthors(authors);
        book.setGenres(genres);
        return bookDao.insert(book);
    }

    /**
     * @param genreDTOS list of genre DTO
     * @return list of corresponding genres from database( if genre not in database insert it and return
     * it with id in result list)
     */
    private List<Genre> getAndInsertGenres(List<GenreDTO> genreDTOS) {
        List<String> genresNames = genreDTOS.stream()
                .map(GenreDTO::getName).collect(Collectors.toList());
        // check if there are genres,which are already in the library
        List<Genre> genresFromDb = genreDao.findGenresByNames(genresNames);
        List<String> genresNamesFromDb = genresFromDb.stream().
                map(Genre::getName).collect(Collectors.toList());
        //get existing genres if they are, create new for those who are not
        List<Genre> resultGenres = new ArrayList<>(genresFromDb);
        // if there are genres who are not in db
        if (genresNames.size() > genresNamesFromDb.size()) {
            List<Genre> genresToSave = genreDTOS.stream().filter(x -> !genresNamesFromDb.contains(x.getName()))
                    .map(modelMapper::dtoToEntity).collect(Collectors.toList());
            for (Genre genre :
                    genresToSave) {
                long idFromDb = genreDao.insert(genre);
                genre.setId(idFromDb);
                resultGenres.add(genre);
            }
        }
        return resultGenres;
    }

    /**
     * @param authorDTOS list of author DTO
     * @return list of corresponding authors from database( if author not in database insert it and return
     * it with id in result list)
     */
    private List<Author> getAndInsertAuthors(List<AuthorDTO> authorDTOS) {
        List<String> authorsNames = authorDTOS.stream()
                .map(AuthorDTO::getFullName).collect(Collectors.toList());
        // check if there are authors,who are already in the library
        List<Author> authorsFromDb = authorDao.findAuthorsByNames(authorsNames);
        //get existing authors if they are, create new for those who are not
        List<Author> resultAuthors = new ArrayList<>(authorsFromDb);
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
                resultAuthors.add(author);
            }
        }
        return resultAuthors;
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
        boolean isUpdated = false;
        if (!bookToUpdate.getTitle().equalsIgnoreCase(targetInfo.getTitle())) {
            bookDao.updateBookTitle(bookToUpdate.getId(), targetInfo.getTitle());
            isUpdated = true;
        }
        boolean isAuthorUpdated = isAuthorUpdated(bookToUpdate, targetInfo);
        isUpdated = isUpdated || isAuthorUpdated;
        boolean isGenreUpdated = isGenreUpdated(bookToUpdate, targetInfo);
        isUpdated = isUpdated || isGenreUpdated;
        return isUpdated;
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }

    private boolean isAuthorUpdated(Book bookToUpdate, BookDTO targetInfo) {
        List<AuthorDTO> targetInfoAuthorDTO = targetInfo.getAuthorDTOS();
        // get list of targetAuthors from database(insert if needed)
        List<Author> targetInfoAuthors = getAndInsertAuthors(targetInfoAuthorDTO);
        List<Author> currentAuthors = bookToUpdate.getAuthors();
        if (!currentAuthors.containsAll(targetInfoAuthors) || !targetInfoAuthors.containsAll(currentAuthors)) {
            long bookId = bookToUpdate.getId();
            //delete all previous book_author relations (
            bookDao.deleteAllAuthorsFromBook(bookId);
            //batch add authors to book
            List<Long> targetAuthorsIds = targetInfoAuthors.stream().map(Author::getId).collect(Collectors.toList());
            bookDao.addNewAuthorsToBook(bookId, targetAuthorsIds);
            return true;
        }
        return false;
    }

    private boolean isGenreUpdated(Book bookToUpdate, BookDTO targetInfo) {
        List<GenreDTO> targetInfoGenreDTO = targetInfo.getGenreDTOS();
        // get list of targetGenres from database(insert if needed)
        List<Genre> targetInfoGenres = getAndInsertGenres(targetInfoGenreDTO);
        List<Genre> currentGenres = bookToUpdate.getGenres();
        if (!currentGenres.containsAll(targetInfoGenres) || !targetInfoGenres.containsAll(currentGenres)) {
            long bookId = bookToUpdate.getId();
            //delete all previous book_genre relations (
            bookDao.deleteAllGenresFromBook(bookId);
            //batch add genres to book
            List<Long> targetGenresIds = targetInfoGenres.stream().map(Genre::getId).collect(Collectors.toList());
            bookDao.addNewGenresToBook(bookId, targetGenresIds);
            return true;
        }
        return false;
    }

}
