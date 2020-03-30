package otus.deryagina.spring.library.data.metrics.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.library.data.metrics.dao.AuthorRepository;
import otus.deryagina.spring.library.data.metrics.domain.Author;
import otus.deryagina.spring.library.data.metrics.dto.AuthorDTO;
import otus.deryagina.spring.library.data.metrics.mapper.ModelMapper;


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
