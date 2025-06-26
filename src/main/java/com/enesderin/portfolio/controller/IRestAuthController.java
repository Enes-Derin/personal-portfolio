package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.jwt.jwtDto.AuthRequest;
import com.enesderin.portfolio.jwt.jwtDto.AuthResponse;
import com.enesderin.portfolio.jwt.jwtDto.DtoUser;
import com.enesderin.portfolio.jwt.jwtDto.RefreshTokenRequest;
import com.enesderin.portfolio.model.RootEntity;
import org.springframework.http.ResponseEntity;

public interface IRestAuthController {

    //RootEntity<DtoUser> register(AuthRequest authRequest);
    RootEntity<AuthResponse> authenticate(AuthRequest authRequest);
    RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
