package otus.deryagina.spring.library.data.mvc.services;


import otus.deryagina.spring.library.data.mvc.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
