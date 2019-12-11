package otus.deryagina.spring.libraryjdbc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.dao.GenreDao;
import otus.deryagina.spring.libraryjdbc.domain.Genre;
import otus.deryagina.spring.libraryjdbc.dto.GenreDTO;
import otus.deryagina.spring.libraryjdbc.mapper.EntityToDtoMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public List<GenreDTO> findAll() {
        List<Genre> genres = genreDao.findAll();
        if( genres == null || genres.isEmpty()){
            return new ArrayList<>();
        }
        return entityToDtoMapper.genreEntityListToGenreDtoList(genres);
    }

    @Override
    public GenreDTO findByName(String genreName) {
        Genre genre = genreDao.findGenreByName(genreName);
        if( genre == null){
            return null;
        }
        return entityToDtoMapper.entityToDto(genre);
    }
}
