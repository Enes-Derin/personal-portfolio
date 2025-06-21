package com.enesderin.portfolio.service;

import com.enesderin.portfolio.dto.AuthRequest;
import com.enesderin.portfolio.dto.DtoUser;

public interface IAuthenticationService  {

    public DtoUser register(AuthRequest authRequest);
}
