package otus.deryagina.spring.library.security.acl.mvc.services;


import otus.deryagina.spring.library.security.acl.mvc.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
