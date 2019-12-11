package otus.deryagina.spring.libraryjdbc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.domain.Genre;
import otus.deryagina.spring.libraryjdbc.dto.AuthorDTO;
import otus.deryagina.spring.libraryjdbc.dto.BookDTO;
import otus.deryagina.spring.libraryjdbc.dto.GenreDTO;

import java.util.List;


@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {
    @Mappings({
            @Mapping(source = "authors", target = "authorDTOS"),
            @Mapping(source = "genres", target = "genreDTOS")
    })
    BookDTO entityToDto(Book book);
    List<BookDTO>  entityToDto(List<Book> books);
    List<GenreDTO> genreEntityListToGenreDtoList(List<Genre> genres);
    AuthorDTO entityToDto(Author author);
    GenreDTO entityToDto(Genre genre);
}
