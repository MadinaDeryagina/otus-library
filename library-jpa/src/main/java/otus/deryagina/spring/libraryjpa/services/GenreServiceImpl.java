package otus.deryagina.spring.libraryjpa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjpa.dao.GenreDao;
import otus.deryagina.spring.libraryjpa.domain.Genre;
import otus.deryagina.spring.libraryjpa.dto.GenreDTO;
import otus.deryagina.spring.libraryjpa.mapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final ModelMapper modelMapper;

    @Override
    public List<GenreDTO> findAll() {
        List<Genre> genres = genreDao.findAll();
        if( genres == null || genres.isEmpty()){
            return new ArrayList<>();
        }
        return modelMapper.genreEntityListToGenreDtoList(genres);
    }

    @Override
    public GenreDTO findByName(String genreName) {
        Optional<Genre> genre = genreDao.findGenreByName(genreName);
        return genre.map(modelMapper::entityToDto).orElse(null);
    }
}
