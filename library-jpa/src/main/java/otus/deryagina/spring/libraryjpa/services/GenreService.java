package otus.deryagina.spring.libraryjpa.services;


import otus.deryagina.spring.libraryjpa.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
