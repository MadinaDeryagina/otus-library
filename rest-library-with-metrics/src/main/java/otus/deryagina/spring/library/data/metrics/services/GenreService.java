package otus.deryagina.spring.library.data.metrics.services;



import otus.deryagina.spring.library.data.metrics.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
