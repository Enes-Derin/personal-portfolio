package com.enesderin.portfolio.jwt.jwtDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser {

    private String username;
    private String password;
}
