package otus.deryagina.spring.library.data.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import otus.deryagina.spring.library.data.jpa.domain.Author;
import otus.deryagina.spring.library.data.jpa.domain.Book;
import otus.deryagina.spring.library.data.jpa.domain.Comment;
import otus.deryagina.spring.library.data.jpa.domain.Genre;
import otus.deryagina.spring.library.data.jpa.dto.AuthorDTO;
import otus.deryagina.spring.library.data.jpa.dto.BookDTO;
import otus.deryagina.spring.library.data.jpa.dto.CommentDTO;
import otus.deryagina.spring.library.data.jpa.dto.GenreDTO;


import java.util.List;


@Mapper(componentModel = "spring")
public interface ModelMapper {
    @Mappings({
            @Mapping(source = "authors", target = "authorDTOS"),
            @Mapping(source = "genres", target = "genreDTOS")
    })
    BookDTO entityToDto(Book book);

    List<BookDTO> entityToDto(List<Book> books);

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

    @Named("mapWithoutId")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "authorDTOS", target = "authors"),
            @Mapping(source = "genreDTOS", target = "genres")
    })
    Book dtoToEntity(BookDTO bookDTO);

    @Named("mapWithoutId")
    @Mapping(target = "id", ignore = true)
    Comment dtoToEntity(CommentDTO commentDTO);

    List<CommentDTO> commentEntitiesToDTOS(List<Comment> comments);
}
