package otus.deryagina.spring.library.data.jpa.services;



import otus.deryagina.spring.library.data.jpa.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
