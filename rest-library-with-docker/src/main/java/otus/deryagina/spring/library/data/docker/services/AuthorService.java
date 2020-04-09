package otus.deryagina.spring.library.data.docker.services;



import otus.deryagina.spring.library.data.docker.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
