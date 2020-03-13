package otus.deryagina.spring.library.security.mvc.services;


import otus.deryagina.spring.library.security.mvc.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
