package com.enesderin.portfolio.service.impl;

import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import com.enesderin.portfolio.exception.BaseException;
import com.enesderin.portfolio.exception.ErrorMessage;
import com.enesderin.portfolio.exception.MessageType;
import com.enesderin.portfolio.model.About;
import com.enesderin.portfolio.repository.AboutRepository;
import com.enesderin.portfolio.service.IAboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AboutServiceImpl implements IAboutService {

    private final AboutRepository aboutRepository;
    private final String uploadDir = "src/main/resources/static/img/";

    @Override
    public AboutResponse createAbout(AboutRequest aboutRequest) throws IOException {
        // Eğer zaten bir kayıt varsa, yeni kayıt oluşturma
        if (!aboutRepository.findAll().isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.ALREADY_EXIST, null));
        }

        String fileName = UUID.randomUUID() + "_" + aboutRequest.getImageUrl().getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, aboutRequest.getImageUrl().getBytes());

        About about = new About();
        BeanUtils.copyProperties(aboutRequest, about);
        about.setImageUrl("/img/" + fileName); // <== 🔥 Önemli kısım
        aboutRepository.save(about);

        AboutResponse response = new AboutResponse();
        BeanUtils.copyProperties(about, response);
        return response;
    }

    @Override
    public List<AboutResponse> getAbout() {
        List<About> list = aboutRepository.findAll();
        List<AboutResponse> responseList = new ArrayList<>();
        for (About about : list) {
            AboutResponse response = new AboutResponse();
            BeanUtils.copyProperties(about, response);
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public AboutResponse updateAbout(int id, AboutRequest aboutRequest) {
        About about = aboutRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXİST, id + "")));

        BeanUtils.copyProperties(aboutRequest, about, "id", "imageUrl");

        if (aboutRequest.getImageUrl() != null && !aboutRequest.getImageUrl().isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + aboutRequest.getImageUrl().getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, aboutRequest.getImageUrl().getBytes());
                about.setImageUrl("/img/" + fileName); // 🔥 Public erişim
            } catch (IOException e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON, "Görsel yükleme hatası"));
            }
        }

        aboutRepository.save(about);
        AboutResponse response = new AboutResponse();
        BeanUtils.copyProperties(about, response);
        return response;
    }
}

