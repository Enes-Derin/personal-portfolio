package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.dto.AuthRequest;
import com.enesderin.portfolio.dto.AuthResponse;
import com.enesderin.portfolio.dto.DtoUser;
import com.enesderin.portfolio.dto.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface IRestAuthController {

    ResponseEntity<DtoUser> register(AuthRequest authRequest);
    ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest);
    ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
