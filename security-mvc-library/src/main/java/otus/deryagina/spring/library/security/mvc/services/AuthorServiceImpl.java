package otus.deryagina.spring.library.security.mvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.library.security.mvc.dao.AuthorRepository;
import otus.deryagina.spring.library.security.mvc.domain.Author;
import otus.deryagina.spring.library.security.mvc.dto.AuthorDTO;
import otus.deryagina.spring.library.security.mvc.mapper.ModelMapper;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AuthorDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        if( authors == null || authors.isEmpty()){
            return new ArrayList<>();
        }
        return modelMapper.authorEntityListToAuthorDtoList(authors);
    }
}
