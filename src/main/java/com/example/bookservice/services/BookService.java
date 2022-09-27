package com.example.bookservice.services;

import com.example.bookservice.dtos.BookDetailDTO;
import com.example.bookservice.entities.Book;

public interface BookService {
    Book create(Book book);
    BookDetailDTO viewDetail(String uuid);
}
