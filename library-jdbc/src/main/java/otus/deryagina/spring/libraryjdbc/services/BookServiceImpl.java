package otus.deryagina.spring.libraryjdbc.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.iostreams.IOStreamsProvider;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final IOStreamsProvider ioStreamsProvider;

    @Override
    public void addBook() {

    }

}
