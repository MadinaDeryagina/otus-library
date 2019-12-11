package otus.deryagina.spring.libraryjdbc.services;

import otus.deryagina.spring.libraryjdbc.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
