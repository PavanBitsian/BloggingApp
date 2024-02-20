package com.nanosoft.BloggingApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BloggingAppController{

    @GetMapping("/")
    public String home(){

        String str="<html> <body> <h1> Welcome to Blogging Application</h1></body></html>";
        return str;

    }

}