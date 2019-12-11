package otus.deryagina.spring.libraryjdbc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.dao.BookDao;
import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.domain.Genre;
import otus.deryagina.spring.libraryjdbc.dto.BookDTO;
import otus.deryagina.spring.libraryjdbc.iostreams.IOStreamsProvider;
import otus.deryagina.spring.libraryjdbc.mapper.EntityToDtoMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final IOStreamsProvider ioStreamsProvider;
    private final BookDao bookDao;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public void addBook() {
        //check that all of genres of book presented in genres in system
    }

    @Override
    public List<BookDTO> findAllBooks() {
        List<BookDTO> resultList = new ArrayList<>();
        List<Book> listOfBooks = bookDao.findAll();
        if (listOfBooks == null || listOfBooks.isEmpty()) {
            return resultList;
        }
        resultList = entityToDtoMapper.entityToDto(listOfBooks);
        return resultList;
    }

    @Override
    public BookDTO findBookById(long id) {
        Book book = bookDao.findById(id);
        if (book == null) {
            return null;
        }
        return entityToDtoMapper.entityToDto(book);
    }

}
