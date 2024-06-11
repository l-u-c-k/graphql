package com.learning.graphql.resolver;

import com.learning.graphql.dto.BookDto;
import com.learning.graphql.exception.BookNotFoundException;
import com.learning.graphql.model.Book;
import com.learning.graphql.repository.BookRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private Query query;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllBooks(){

        //mocking the data
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1L, "Monalisa","Krishnan","novel",25.89));
        mockBooks.add(new Book(2L, "The Warrior","Murthy","fiction",250.89));
        when(bookRepository.findAll()).thenReturn(mockBooks);
        List<BookDto> result = query.getAllBooks();
        assertEquals(mockBooks.size(),result.size());
        for (int i = 0;i<mockBooks.size();i++){
            assertEquals(mockBooks.get(i).getId(),result.get(i).getId());
            assertEquals(mockBooks.get(i).getTitle(),result.get(i).getTitle());
            assertEquals(mockBooks.get(i).getAuthor(),result.get(i).getAuthor());
            assertEquals(mockBooks.get(i).getGenre(),result.get(i).getGenre());
            assertEquals(mockBooks.get(i).getPrice(),result.get(i).getPrice());
        }
        verify(bookRepository,times(1)).findAll();
    }

    @Test
    void testGetBookById(){
        Book mockBook = new Book(1L, "Monalisa","Krishnan","novel",25.89);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));
        BookDto result = query.getBookById(1L);
        assertEquals(mockBook.getId(),result.getId());
        assertEquals(mockBook.getTitle(),result.getTitle());
        assertEquals(mockBook.getAuthor(),result.getAuthor());

        verify(bookRepository,times(1)).findById(1L);
    }

    @Test
    void testGetBookById_BookNotFoundException(){
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,() -> query.getBookById(1L));
        verify(bookRepository,times(1)).findById(1L);
    }

}
