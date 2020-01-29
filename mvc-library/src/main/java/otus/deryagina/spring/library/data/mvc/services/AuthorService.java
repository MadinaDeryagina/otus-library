package otus.deryagina.spring.library.data.mvc.services;



import otus.deryagina.spring.library.data.mvc.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
