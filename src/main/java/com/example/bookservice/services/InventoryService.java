package com.example.bookservice.services;

import com.example.bookservice.common.Common;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${inventory_service_name}", url = "${inventory_service_url}")
public interface InventoryService {
    @GetMapping(Common.PATH_GET_BOOK_QUANTITY_BY_ID)
    int getQuantity(@PathVariable String id);
}
