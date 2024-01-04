package com.project1.domain.member.controller;

import com.project1.domain.member.service.Layer1.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthController {

    public AuthController(AuthService authService) {
    }

    @DeleteMapping("/logOut")
    public ResponseEntity logOut(){

        return new ResponseEntity(HttpStatus.OK);
    }
}