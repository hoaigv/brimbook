package com.example.bookshop.converter;

import com.example.bookshop.entity.AuthorEntity;
import com.example.bookshop.repository.AuthorRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Named("AuthorsConverter")
@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true )
@AllArgsConstructor
@Component
public class AuthorsConverter {
    AuthorRepository authorRepository;

    @Named("authorsNameToAuthors")
    public Set<AuthorEntity> authorsNameToAuthors(Set<String> authorsNames) {
        return authorsNames.stream().map(
                name -> {
                    var author = authorRepository.findByAuthorName(name);
                    return Objects
                            .requireNonNullElseGet(author , ()-> authorRepository.save(AuthorEntity.builder().authorName(name).biography("").build())); }
        ).collect(Collectors.toSet());
    }
    @Named("authorsToAuthorsName")
    public Set<String> authorsToAuthorsName(Set<AuthorEntity> authors) {
        return authors.stream().map(AuthorEntity::getAuthorName).collect(Collectors.toSet());
    }

}
