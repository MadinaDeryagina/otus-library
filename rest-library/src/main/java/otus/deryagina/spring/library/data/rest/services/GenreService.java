package otus.deryagina.spring.library.data.rest.services;



import otus.deryagina.spring.library.data.rest.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
