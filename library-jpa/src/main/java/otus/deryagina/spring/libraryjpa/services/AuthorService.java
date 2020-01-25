package otus.deryagina.spring.libraryjpa.services;



import otus.deryagina.spring.libraryjpa.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
