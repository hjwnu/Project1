package com.project1.domain.member.controller;

import com.project1.domain.member.service.Layer1.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @DeleteMapping("/logOut")
    public ResponseEntity<HttpStatus> logOut(String userName){
        authService.deleteRefreshToken(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}