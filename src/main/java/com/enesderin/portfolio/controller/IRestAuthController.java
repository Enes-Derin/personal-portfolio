package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.jwt.jwtDto.AuthRequest;
import com.enesderin.portfolio.jwt.jwtDto.AuthResponse;
import com.enesderin.portfolio.jwt.jwtDto.DtoUser;
import com.enesderin.portfolio.jwt.jwtDto.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface IRestAuthController {

    ResponseEntity<DtoUser> register(AuthRequest authRequest);
    ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest);
    ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
