package com.nanosoft.BloggingApp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    // One syntax to implement a
    // GET method
    @GetMapping("/")
    public String home()
    {
        String str
                = "<html><body><font color=\"green\">"
                + "<h1>Welcome to Nanosoft</h1>"
                + "</font></body></html>";
        return str;
    }

    // Another syntax to implement a
    // GET method
    @RequestMapping(
            method = { RequestMethod.GET },
            value = { "/gfg" })

    public String info()
    {
        String str2
                = "<html><body><font color=\"green\">"
                + "<h2>Nanosoft is a SaaS company"
                + " for Enterprises. "
                + "This blog has been "
                + "created to provide well written, "
                + "well thought and well explained "
                + "solutions for various business use cases."
                + "</h2></font></body></html>";
        return str2;
    }
}
