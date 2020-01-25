package otus.deryagina.spring.library.data.jpa.services;


import otus.deryagina.spring.library.data.jpa.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
