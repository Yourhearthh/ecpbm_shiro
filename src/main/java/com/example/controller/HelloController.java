package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:27
 * @version: 1.0
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
