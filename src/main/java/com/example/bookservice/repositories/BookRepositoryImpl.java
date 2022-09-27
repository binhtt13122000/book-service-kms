package com.example.bookservice.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.example.bookservice.common.ErrorMessages;
import com.example.bookservice.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository{
    @Autowired
    private DynamoDBMapper mapper;
    @Override
    public Book createBook(Book book) throws DynamoDBMappingException {
        mapper.save(book);
        return book;
    }

    @Override
    public Book viewDetail(String uuid) {
        Book book = mapper.load(Book.class, uuid);
        if(book == null){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND);
        }
        return book;
    }
}
