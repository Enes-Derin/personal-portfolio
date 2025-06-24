package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.IRestAuthController;
import com.enesderin.portfolio.jwt.jwtDto.AuthRequest;
import com.enesderin.portfolio.jwt.jwtDto.AuthResponse;
import com.enesderin.portfolio.jwt.jwtDto.DtoUser;
import com.enesderin.portfolio.jwt.jwtDto.RefreshTokenRequest;
import com.enesderin.portfolio.model.RootEntity;
import com.enesderin.portfolio.service.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl extends RestBaseController implements IRestAuthController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    @Override
    public RootEntity<DtoUser> register(@RequestBody @Valid AuthRequest authRequest) {
        return ok(this.authenticationService.register(authRequest));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse authResponse = this.authenticationService.authenticate(authRequest);
        return ok(authResponse);
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return ok(this.authenticationService.refreshToken(refreshTokenRequest));
    }
}
