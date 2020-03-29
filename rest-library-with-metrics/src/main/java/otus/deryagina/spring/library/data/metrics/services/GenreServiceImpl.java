package otus.deryagina.spring.library.data.metrics.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.library.data.metrics.dao.GenreRepository;
import otus.deryagina.spring.library.data.metrics.domain.Genre;
import otus.deryagina.spring.library.data.metrics.dto.GenreDTO;
import otus.deryagina.spring.library.data.metrics.mapper.ModelMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GenreDTO> findAll() {
        List<Genre> genres = genreRepository.findAll();
        if( genres == null || genres.isEmpty()){
            return new ArrayList<>();
        }
        return modelMapper.genreEntityListToGenreDtoList(genres);
    }

    @Override
    public GenreDTO findByName(String genreName) {
        Optional<Genre> genre = genreRepository.findByName(genreName);
        return genre.map(modelMapper::entityToDto).orElse(null);
    }
}
