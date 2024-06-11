package com.learning.graphql.resolver;

import com.learning.graphql.dto.BookDto;
import com.learning.graphql.exception.BookNotFoundException;
import com.learning.graphql.mapper.BookMapper;
import com.learning.graphql.model.Book;
import com.learning.graphql.repository.BookRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {


    private final BookRepository bookRepository;

    @Autowired
    public Mutation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    public BookDto createBook(BookDto bookDto) {
        Book book = BookMapper.dtoToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return BookMapper.entityToDto(savedBook);    }


    public BookDto updateBook(Long id, BookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setTitle(bookDto.getTitle());
            book.setAuthor(bookDto.getAuthor());
            book.setGenre(bookDto.getGenre());
            book.setPrice(bookDto.getPrice());
            Book updatedBook = bookRepository.save(book);
            return BookMapper.entityToDto(updatedBook);
        }
        else {
            throw  new BookNotFoundException("Book not found with id:" + id);
        }    }


    public Boolean deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            bookRepository.deleteById(id);
            return true;
        }
        else {
            throw new BookNotFoundException("Book not found with id:" + id);
        }    }
}
