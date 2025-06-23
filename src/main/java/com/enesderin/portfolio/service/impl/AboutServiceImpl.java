package com.enesderin.portfolio.service.impl;

import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import com.enesderin.portfolio.model.About;
import com.enesderin.portfolio.repository.AboutRepository;
import com.enesderin.portfolio.service.IAboutService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AboutServiceImpl implements IAboutService {

    @Autowired
    private AboutRepository aboutRepository;

    private String uploadDir= "uploads/";

    @Override
    public AboutResponse createAbout(AboutRequest aboutRequest) throws IOException {
        String fileName= UUID.randomUUID() + "_" + aboutRequest.getImageUrl().getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath,aboutRequest.getImageUrl().getBytes());

        About about = new About();
        BeanUtils.copyProperties(aboutRequest, about);
        about.setImageUrl("/"+uploadDir+fileName);
        aboutRepository.save(about);
        AboutResponse aboutResponse = new AboutResponse();
        BeanUtils.copyProperties(about,aboutResponse);
        return aboutResponse;
    }

    @Override
    public AboutResponse getAbout() {
        Optional<About> about = this.aboutRepository.findAll().stream().findFirst();
        if (about.isPresent()) {
                AboutResponse aboutResponse = new AboutResponse();
                BeanUtils.copyProperties(about, aboutResponse);
                return aboutResponse;

        }
        return null;
    }

    @Override
    public AboutResponse updateAbout(int id, AboutRequest aboutRequest) {
        Optional<About> aboutOptional = this.aboutRepository.findById(id);
        if (aboutOptional.isPresent()) {
            About about = aboutOptional.get();
            BeanUtils.copyProperties(aboutRequest, about);
            if (aboutRequest.getImageUrl() != null && !aboutRequest.getImageUrl().isEmpty()) {
                String fileName= UUID.randomUUID() + "_" + aboutRequest.getImageUrl().getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                try {
                    Files.createDirectories(filePath.getParent());
                    Files.write(filePath,aboutRequest.getImageUrl().getBytes());
                    about.setImageUrl("/"+uploadDir+fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            aboutRepository.save(about);
            AboutResponse aboutResponse = new AboutResponse();
            BeanUtils.copyProperties(about, aboutResponse);
            return aboutResponse;
        }

        return null;
    }
}
