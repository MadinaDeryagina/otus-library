package otus.deryagina.spring.libraryjpa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjpa.dao.AuthorDao;
import otus.deryagina.spring.libraryjpa.dao.BookDao;
import otus.deryagina.spring.libraryjpa.dao.CommentDao;
import otus.deryagina.spring.libraryjpa.dao.GenreDao;
import otus.deryagina.spring.libraryjpa.domain.Author;
import otus.deryagina.spring.libraryjpa.domain.Book;
import otus.deryagina.spring.libraryjpa.domain.Comment;
import otus.deryagina.spring.libraryjpa.domain.Genre;
import otus.deryagina.spring.libraryjpa.dto.AuthorDTO;
import otus.deryagina.spring.libraryjpa.dto.BookDTO;
import otus.deryagina.spring.libraryjpa.dto.CommentDTO;
import otus.deryagina.spring.libraryjpa.dto.GenreDTO;
import otus.deryagina.spring.libraryjpa.mapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;
    private final ModelMapper modelMapper;


    @Override
    public Book addAsNewBook(BookDTO bookDTO) {
        List<Author> authors = getAndInsertAuthors(bookDTO.getAuthorDTOS());
        List<Genre> genres = getAndInsertGenres(bookDTO.getGenreDTOS());
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthors(authors);
        book.setGenres(genres);
        return bookDao.save(book);
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
                Genre genreFromDb = genreDao.save(genre);
                resultGenres.add(genreFromDb);
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
                Author authorFromDb = authorDao.save(author);
                resultAuthors.add(authorFromDb);
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
        Optional<Book> book = bookDao.findById(id);
        return book.map(modelMapper::entityToDto).orElse(null);
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
    public void updateBook(long id, BookDTO targetInfo) {
        Book bookToSave = modelMapper.dtoToEntity(targetInfo);
        bookToSave.setId(id);
        bookDao.save(bookToSave);
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void addCommentToBook(CommentDTO commentDTO) throws IllegalArgumentException {
        Optional<Book> book = bookDao.findById(commentDTO.getBookId());
        if (!book.isPresent()) {
            throw new IllegalArgumentException("Invalid book id");
        } else {
            Comment commentToSave = modelMapper.dtoToEntity(commentDTO);
            commentToSave.setBook(book.get());
            commentDao.save(commentToSave);
        }
    }

    @Override
    public List<CommentDTO> showAllCommentsToBook(long bookId) {
        List<Comment> comments = commentDao.findAllCommentsToBook(bookId);
        return modelMapper.commentEntitiesToDTOS(comments);
    }

    @Override
    public void deleteAllCommentsFromBook(long bookId) {
        commentDao.deleteAllCommentsFromBook(bookId);
    }


}
