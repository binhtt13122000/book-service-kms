package com.example.bookservice.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.example.bookservice.dtos.AuthorDTO;
import com.example.bookservice.dtos.BookDetailDTO;
import com.example.bookservice.entities.Book;
import com.example.bookservice.repositories.BookRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private InventoryService inventoryService;
    @Override
    public Book create(Book book) {
        return bookRepository.createBook(book);
    }

    @Override
    public BookDetailDTO viewDetail(String uuid) throws ResourceNotFoundException, FeignException, DynamoDBMappingException {
        Book book = this.bookRepository.viewDetail(uuid);
        AuthorDTO author = this.authorService.getOne(book.getAuthorUuid());
        int quantity = inventoryService.getQuantity(uuid);
        return BookDetailDTO.generate(book, author, quantity);
    }
}
