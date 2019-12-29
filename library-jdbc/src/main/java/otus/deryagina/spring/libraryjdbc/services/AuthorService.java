package otus.deryagina.spring.libraryjdbc.services;

import otus.deryagina.spring.libraryjdbc.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
