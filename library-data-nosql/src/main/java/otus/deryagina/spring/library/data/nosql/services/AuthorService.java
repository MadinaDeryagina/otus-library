package otus.deryagina.spring.library.data.nosql.services;



import otus.deryagina.spring.library.data.nosql.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
