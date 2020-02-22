package otus.deryagina.spring.library.data.rest.services;



import otus.deryagina.spring.library.data.rest.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
