package otus.deryagina.spring.library.data.metrics.services;



import otus.deryagina.spring.library.data.metrics.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
