package com.example.bookservice.repositories;

import com.example.bookservice.entities.Book;

public interface BookRepository {
    Book createBook(Book book);
    Book viewDetail(String uuid);
}
