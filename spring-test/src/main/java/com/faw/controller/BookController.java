package com.faw.controller;

import com.faw.dao.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 鹿胜宝
 * @date 2023/04/25
 */
@RestController
@RequestMapping("/books")
public class BookController {

//    @GetMapping
//    public String list() {
//        System.out.println("running");
//        return "books";
//    }

    @GetMapping
    public Book getList() {
        System.out.println("running");
        return new Book("1","Spring");
    }
}
