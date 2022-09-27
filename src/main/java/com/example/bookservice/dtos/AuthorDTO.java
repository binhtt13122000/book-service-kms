package com.example.bookservice.dtos;

import lombok.Data;

@Data
public class AuthorDTO {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
}
