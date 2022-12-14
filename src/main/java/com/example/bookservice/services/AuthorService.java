package com.example.bookservice.services;

import com.example.bookservice.common.Common;
import com.example.bookservice.dtos.AuthorDTO;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${author_service.name}", url = "${author_service.endpoint}")
public interface AuthorService {
    @GetMapping(Common.PATH_GET_ONE)
    AuthorDTO getOne(@PathVariable String id) throws FeignException;
}
