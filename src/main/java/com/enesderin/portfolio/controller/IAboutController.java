package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import org.springframework.http.ResponseEntity;

public interface IAboutController {
    ResponseEntity<AboutResponse> createAbout(AboutRequest aboutRequest);
    ResponseEntity<AboutResponse> updateAbout(int id,AboutRequest aboutRequest);
    ResponseEntity<AboutResponse> getAbout();
}
