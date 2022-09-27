package com.example.bookservice.controllers;

import com.example.bookservice.common.Common;
import com.example.bookservice.dtos.BookDetailDTO;
import com.example.bookservice.dtos.CreateBookDTO;
import com.example.bookservice.entities.Book;
import com.example.bookservice.services.BookService;
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
        BookDetailDTO dto = this.bookService.viewDetail(id);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
}
