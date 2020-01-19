package otus.deryagina.spring.libraryjdbc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.dao.AuthorDao;
import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.dto.AuthorDTO;
import otus.deryagina.spring.libraryjdbc.mapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final ModelMapper modelMapper;

    @Override
    public List<AuthorDTO> findAll() {
        List<Author> authors = authorDao.findAll();
        if( authors == null || authors.isEmpty()){
            return new ArrayList<>();
        }
        return modelMapper.authorEntityListToAuthorDtoList(authors);
    }
}
