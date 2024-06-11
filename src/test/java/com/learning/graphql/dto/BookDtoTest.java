package com.learning.graphql.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookDtoTest {

    @Test
    void testConstructorAndGetters(){
        Long id = 1L;
        String title = "title1";
        String auhtor = "author1";
        String genre = "novel";
        double price = 20.89;
        BookDto bookDto = new BookDto(id,title,auhtor,genre,price);

        //testing the constructor
        assertNotNull(bookDto);
        assertEquals(id,bookDto.getId());
        assertEquals(title,bookDto.getTitle());
        assertEquals(auhtor,bookDto.getAuthor());
        assertEquals(genre,bookDto.getGenre());
        assertEquals(price,bookDto.getPrice());

        //testing defaultconstructor
        BookDto defaultDto = new BookDto();
        assertNotNull(defaultDto);
        assertNull(defaultDto.getId());
        assertNull(defaultDto.getTitle());
        assertNull(defaultDto.getAuthor());
        assertNull(defaultDto.getGenre());
        assertEquals(0.0,defaultDto.getPrice());

    }

    @Test
    void testSetters(){
        BookDto bookDto = new BookDto();

        Long id = 2L;
        String title = "Update title";
        String author = "Update author";
        String genre = "Update genre";
        double price = 56.67;
        bookDto.setId(id);
        bookDto.setTitle(title);
        bookDto.setAuthor(author);
        bookDto.setGenre(genre);
        bookDto.setPrice(price);
        assertEquals(id, bookDto.getId());
        assertEquals(title, bookDto.getTitle());
        assertEquals(author, bookDto.getAuthor());
        assertEquals(genre, bookDto.getGenre());
        assertEquals(price, bookDto.getPrice());
    }
}
