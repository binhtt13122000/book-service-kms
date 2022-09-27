package com.example.bookservice.dtos;

import com.example.bookservice.entities.Book;
import lombok.Data;

@Data
public class BookDetailDTO {
    private String uuid;
    private String title;
    private String isbn;
    private AuthorDTO author;
    private int quantity;

    public static BookDetailDTO generate(Book book, AuthorDTO author, int quantity){
        BookDetailDTO bookDetailDTO = new BookDetailDTO();
        bookDetailDTO.setAuthor(author);
        bookDetailDTO.setIsbn(book.getIsbn());
        bookDetailDTO.setTitle(book.getTitle());
        bookDetailDTO.setUuid(book.getUuid());
        bookDetailDTO.setQuantity(quantity);
        return bookDetailDTO;
    }
}
