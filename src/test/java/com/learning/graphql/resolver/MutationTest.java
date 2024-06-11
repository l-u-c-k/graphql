package com.learning.graphql.resolver;

import com.learning.graphql.dto.BookDto;
import com.learning.graphql.exception.BookNotFoundException;
import com.learning.graphql.mapper.BookMapper;
import com.learning.graphql.model.Book;
import com.learning.graphql.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MutationTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private Mutation mutation;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateBook(){

        BookDto bookDto = new BookDto(1L,"Monalisa","Krishnan","novel",252.89);
        when(bookRepository.save(any())).thenReturn(BookMapper.dtoToEntity(bookDto));
        BookDto result = mutation.createBook(bookDto);
        assertNotNull(result);
        assertEquals(bookDto.getTitle(),result.getTitle());
        assertEquals(bookDto.getAuthor(),result.getAuthor());
        assertEquals(bookDto.getGenre(),result.getGenre());
        assertEquals(bookDto.getPrice(),result.getPrice());

        verify(bookRepository,times(1)).save(any());
    }

    @Test
    void testUpdateBook(){
        Long id = 1L;
        BookDto bookDto = new BookDto(id,"Updated Book Title","Updated Book Author","Updated Book Genre",20.89);
        Book existingBook = new Book(id,"Monalisa","Krishnan","novel",252.89);
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        BookDto result = mutation.updateBook(id,bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getId(),result.getId());
        assertEquals(bookDto.getTitle(),result.getTitle());
        assertEquals(bookDto.getAuthor(),result.getAuthor());
        assertEquals(bookDto.getGenre(),result.getGenre());
        assertEquals(bookDto.getPrice(),result.getPrice());

        verify(bookRepository,times(1)).findById(id);
        verify(bookRepository,times(1)).save(any());
    }
    @Test
    void testUpdateBook_BookNotFound(){
        Long id = 1L;
        BookDto bookDto = new BookDto(id, "Updated Book", "Updated Author", "Updated Genre", 20.0);
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,() -> mutation.updateBook(id,bookDto));
        verify(bookRepository,times(1)).findById(id);
        verify(bookRepository,never()).save(any());
    }

    @Test
    void testDeleteBook(){
        Long id = 1L;
        Book existingBook = new Book(id,"Monalisa","Krishnan","novel",252.89);
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        Boolean result = mutation.deleteBook(id);
        assertTrue(result);
        verify(bookRepository,times(1)).findById(id);
        verify(bookRepository,times(1)).deleteById(id);
    }

    @Test
    void testDeleteBook_BookNotFound(){
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,() -> mutation.deleteBook(id));
        verify(bookRepository,times(1)).findById(id);
        verify(bookRepository,never()).deleteById(anyLong());
    }
}
