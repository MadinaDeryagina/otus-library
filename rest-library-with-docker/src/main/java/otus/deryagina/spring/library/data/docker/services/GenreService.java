package otus.deryagina.spring.library.data.docker.services;



import otus.deryagina.spring.library.data.docker.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
