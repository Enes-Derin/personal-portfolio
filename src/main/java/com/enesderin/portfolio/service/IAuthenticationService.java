package com.enesderin.portfolio.service;

import com.enesderin.portfolio.jwt.jwtDto.AuthRequest;
import com.enesderin.portfolio.jwt.jwtDto.AuthResponse;
import com.enesderin.portfolio.jwt.jwtDto.DtoUser;
import com.enesderin.portfolio.jwt.jwtDto.RefreshTokenRequest;

public interface IAuthenticationService  {

    //DtoUser register(AuthRequest authRequest);
    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
