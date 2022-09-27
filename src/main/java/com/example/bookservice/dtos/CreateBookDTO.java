package com.example.bookservice.dtos;

import com.example.bookservice.common.ErrorMessages;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBookDTO {
    @NotBlank(message = ErrorMessages.NOT_NULL_TITLE_ERROR_MESSAGE)
    private String title;
    @NotBlank(message = ErrorMessages.NOT_NULL_ISBN_ERROR_MESSAGE)
    private String isbn;
    @NotBlank(message = ErrorMessages.NOT_NULL_AUTHOR_ID_ERROR_MESSAGE)
    private String authorUuid;
}
