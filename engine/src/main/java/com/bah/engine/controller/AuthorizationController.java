package com.bah.engine.controller;

import com.bah.engine.model.AuthorizationResponseDto;
import com.bah.engine.model.LoginDto;
import com.bah.engine.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponseDto> login(@RequestBody LoginDto loginDto) {
       return ResponseEntity.ok(authorizationService.login(loginDto));
    }

}
