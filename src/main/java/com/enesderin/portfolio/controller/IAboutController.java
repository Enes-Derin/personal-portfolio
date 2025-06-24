package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import com.enesderin.portfolio.model.RootEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAboutController {
    RootEntity<AboutResponse> createAbout(AboutRequest aboutRequest);
    RootEntity<AboutResponse> updateAbout(int id,AboutRequest aboutRequest);
    RootEntity<List<AboutResponse>> getAbout();
}
