package otus.deryagina.spring.library.security.mvc.services;


import otus.deryagina.spring.library.security.mvc.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
