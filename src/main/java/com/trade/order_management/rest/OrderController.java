package com.trade.order_management.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("test");
    }
}
