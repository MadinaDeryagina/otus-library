package otus.deryagina.spring.libraryjpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import otus.deryagina.spring.libraryjpa.domain.Author;
import otus.deryagina.spring.libraryjpa.domain.Book;
import otus.deryagina.spring.libraryjpa.domain.Genre;
import otus.deryagina.spring.libraryjpa.dto.AuthorDTO;
import otus.deryagina.spring.libraryjpa.dto.BookDTO;
import otus.deryagina.spring.libraryjpa.dto.GenreDTO;


import java.util.List;


@Mapper(componentModel = "spring")
public interface ModelMapper {
    @Mappings({
            @Mapping(source = "authors", target = "authorDTOS"),
            @Mapping(source = "genres", target = "genreDTOS")
    })
    BookDTO entityToDto(Book book);
    List<BookDTO>  entityToDto(List<Book> books);
    List<GenreDTO> genreEntityListToGenreDtoList(List<Genre> genres);
    List<AuthorDTO> authorEntityListToAuthorDtoList(List<Author> authors);
    AuthorDTO entityToDto(Author author);
    GenreDTO entityToDto(Genre genre);
    @Named("mapWithoutId")
    @Mapping(target = "id", ignore = true)
    Author dtoToEntity(AuthorDTO authorDTO);

    @Named("mapWithoutId")
    @Mapping(target = "id", ignore = true)
    Genre dtoToEntity(GenreDTO genreDTO);
}
