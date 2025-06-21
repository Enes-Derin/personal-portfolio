package com.enesderin.portfolio.service;

import com.enesderin.portfolio.dto.AuthRequest;
import com.enesderin.portfolio.dto.AuthResponse;
import com.enesderin.portfolio.dto.DtoUser;
import com.enesderin.portfolio.dto.RefreshTokenRequest;

public interface IAuthenticationService  {

    DtoUser register(AuthRequest authRequest);
    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
