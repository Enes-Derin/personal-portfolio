package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.IRestAuthController;
import com.enesderin.portfolio.dto.AuthRequest;
import com.enesderin.portfolio.dto.AuthResponse;
import com.enesderin.portfolio.dto.DtoUser;
import com.enesderin.portfolio.dto.RefreshTokenRequest;
import com.enesderin.portfolio.service.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    @Override
    public ResponseEntity<DtoUser> register(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(this.authenticationService.register(authRequest));
    }

    @PostMapping("/authenticate")
    @Override
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse authResponse = this.authenticationService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refreshToken")
    @Override
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(this.authenticationService.refreshToken(refreshTokenRequest));
    }
}
