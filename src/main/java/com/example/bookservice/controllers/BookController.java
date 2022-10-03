package com.example.bookservice.controllers;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.example.bookservice.common.Common;
import com.example.bookservice.dtos.BookDetailDTO;
import com.example.bookservice.dtos.CreateBookDTO;
import com.example.bookservice.entities.Book;
import com.example.bookservice.services.BookService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity create(@Valid @RequestBody CreateBookDTO body){
        Book book = Book.createBook(body);
        book = this.bookService.create(book);
        return new ResponseEntity(book, HttpStatus.OK);
    }

    @GetMapping(Common.PATH_GET_ONE)
    public ResponseEntity getOne(@PathVariable String id){
        try {
            BookDetailDTO dto = this.bookService.viewDetail(id);
            return new ResponseEntity(dto, HttpStatus.OK);
        } catch (ResourceNotFoundException ex){
            return new ResponseEntity(ex.getErrorMessage(), HttpStatus.NOT_FOUND);
        } catch (FeignException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
