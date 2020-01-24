package otus.deryagina.spring.library.data.nosql.services;


import otus.deryagina.spring.library.data.nosql.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
