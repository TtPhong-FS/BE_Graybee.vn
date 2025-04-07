package vn.graybee.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/redirect")
    public ResponseEntity<Void> redirectToAnotherController() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/demo/home"))
                .build();
    }

    @GetMapping("/home")
    public String getHome() {
        return "Ban da duoc dieu huong den hom";
    }

}
