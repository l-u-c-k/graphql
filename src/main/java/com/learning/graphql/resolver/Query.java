package com.learning.graphql.resolver;

import com.learning.graphql.dto.BookDto;
import com.learning.graphql.exception.BookNotFoundException;
import com.learning.graphql.mapper.BookMapper;
import com.learning.graphql.model.Book;
import com.learning.graphql.repository.BookRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Query implements GraphQLQueryResolver {

    private final BookRepository bookRepository;

    GraphQLScalarType longScalar = ExtendedScalars.newAliasedScalar("Long")
            .aliasedScalar(ExtendedScalars.GraphQLLong)
            .build();

    @Autowired
    public Query(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper::entityToDto).collect(Collectors.toList());
    }


    public BookDto getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isPresent()){
            return BookMapper.entityToDto(bookOptional.get());
        }
        else {
            throw new BookNotFoundException("Book not found with id:" + id);
        }    }
}
