package otus.deryagina.spring.library.data.docker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import otus.deryagina.spring.library.data.docker.domain.Author;
import otus.deryagina.spring.library.data.docker.domain.Book;
import otus.deryagina.spring.library.data.docker.domain.Comment;
import otus.deryagina.spring.library.data.docker.domain.Genre;
import otus.deryagina.spring.library.data.docker.dto.AuthorDTO;
import otus.deryagina.spring.library.data.docker.dto.BookDTO;
import otus.deryagina.spring.library.data.docker.dto.GenreDTO;
import otus.deryagina.spring.library.data.docker.dto.CommentDTO;


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

    @Mapping(target = "bookId", source = "book.id")
    CommentDTO dtoToEntity(Comment comment);

    List<CommentDTO> commentEntitiesToDTOS(List<Comment> comments);
}
