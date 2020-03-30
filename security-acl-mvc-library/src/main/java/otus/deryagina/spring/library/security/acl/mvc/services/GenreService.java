package otus.deryagina.spring.library.security.acl.mvc.services;


import otus.deryagina.spring.library.security.acl.mvc.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> findAll();
    GenreDTO findByName(String genreName);
}
