package com.learning.graphql.mapper;

import com.learning.graphql.dto.BookDto;
import com.learning.graphql.model.Book;

public class BookMapper {
    public static BookDto entityToDto(Book book){
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPrice(book.getPrice());
        return dto;
    }

    public static Book dtoToEntity(BookDto bookDto){
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setPrice(bookDto.getPrice());
        return book;

    }
}
