package com.enesderin.portfolio.service.impl;

import com.enesderin.portfolio.exception.BaseException;
import com.enesderin.portfolio.exception.ErrorMessage;
import com.enesderin.portfolio.exception.MessageType;
import com.enesderin.portfolio.jwt.jwtDto.AuthRequest;
import com.enesderin.portfolio.jwt.jwtDto.AuthResponse;
import com.enesderin.portfolio.jwt.jwtDto.DtoUser;
import com.enesderin.portfolio.jwt.jwtDto.RefreshTokenRequest;
import com.enesderin.portfolio.jwt.JWTService;
import com.enesderin.portfolio.model.RefreshToken;
import com.enesderin.portfolio.model.Role;
import com.enesderin.portfolio.model.User;
import com.enesderin.portfolio.repository.RefreshTokenRepository;
import com.enesderin.portfolio.repository.UserRepository;
import com.enesderin.portfolio.service.IAuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }


   @Override
    public DtoUser register(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(user, dtoUser);
        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (authRequest.getUsername(), authRequest.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<User> user = userRepository.findByUsername(authRequest.getUsername());
            String accessToken = jwtService.generateToken(user.get());

            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user.get()));

            return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());

        }catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAMEORPASSWORDINVALID,null));
        }
    }

    public boolean isValidRefreshToken(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optionalRefreshToken = this.refreshTokenRepository.findByRefreshToken(request.getRefreshToken());
        if(optionalRefreshToken.isEmpty()) {
            return null;
            // Exception Handling
        }
        if (!isValidRefreshToken(optionalRefreshToken.get().getExpiredDate())){
            return null;
            // exception Handling
        }
        User user = optionalRefreshToken.get().getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());



    }
}
