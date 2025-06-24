package com.enesderin.portfolio.service;

import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAboutService {
    AboutResponse createAbout(AboutRequest aboutRequest) throws IOException;
    List<AboutResponse> getAbout();
    AboutResponse updateAbout(int id, AboutRequest aboutRequest);
}
