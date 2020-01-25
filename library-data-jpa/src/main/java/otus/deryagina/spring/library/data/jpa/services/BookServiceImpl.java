package otus.deryagina.spring.library.data.jpa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.library.data.jpa.dao.*;
import otus.deryagina.spring.library.data.jpa.domain.Author;
import otus.deryagina.spring.library.data.jpa.domain.Book;
import otus.deryagina.spring.library.data.jpa.domain.Comment;
import otus.deryagina.spring.library.data.jpa.domain.Genre;
import otus.deryagina.spring.library.data.jpa.dto.AuthorDTO;
import otus.deryagina.spring.library.data.jpa.dto.BookDTO;
import otus.deryagina.spring.library.data.jpa.dto.CommentDTO;
import otus.deryagina.spring.library.data.jpa.dto.GenreDTO;
import otus.deryagina.spring.library.data.jpa.mapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;


    @Override
    public Book addAsNewBook(BookDTO bookDTO) {
        List<Author> authors = getAndInsertAuthors(bookDTO.getAuthorDTOS());
        List<Genre> genres = getAndInsertGenres(bookDTO.getGenreDTOS());
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthors(authors);
        book.setGenres(genres);
        return bookRepository.save(book);
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
        List<Genre> genresFromDb = genreRepository.findAllByNameIn(genresNames);
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
                Genre genreFromDb = genreRepository.save(genre);
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
        List<Author> authorsFromDb = authorRepository.findAllByFullNameIn(authorsNames);
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
                Author authorFromDb = authorRepository.save(author);
                resultAuthors.add(authorFromDb);
            }
        }
        return resultAuthors;

    }


    @Override
    public List<BookDTO> findAllBooks() {
        List<BookDTO> resultList = new ArrayList<>();
        List<Book> listOfBooks = bookRepository.findAll();
        if (listOfBooks == null || listOfBooks.isEmpty()) {
            return resultList;
        }
        resultList = modelMapper.entityToDto(listOfBooks);
        return resultList;
    }

    @Override
    public BookDTO findBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(modelMapper::entityToDto).orElse(null);
    }

    @Override
    public List<BookDTO> findBooksByTitle(String title) {
        List<Book> books = bookRepository.findAllByTitle(title);
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
        bookRepository.save(bookToSave);
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void addCommentToBook(CommentDTO commentDTO) throws IllegalArgumentException {
        Optional<Book> book = bookRepository.findById(commentDTO.getBookId());
        if (!book.isPresent()) {
            throw new IllegalArgumentException("Invalid book id");
        } else {
            Comment commentToSave = modelMapper.dtoToEntity(commentDTO);
            commentToSave.setBook(book.get());
            commentRepository.save(commentToSave);
        }
    }

    @Override
    public List<CommentDTO> showAllCommentsToBook(long bookId) {
        List<Comment> comments = commentRepository.findAllByBook_Id(bookId);
        return modelMapper.commentEntitiesToDTOS(comments);
    }

    @Override
    public void deleteAllCommentsFromBook(long bookId) {
        commentRepository.deleteAllByBook_Id(bookId);
    }


}
