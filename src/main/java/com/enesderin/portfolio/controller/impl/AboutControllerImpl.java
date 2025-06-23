package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.IAboutController;
import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import com.enesderin.portfolio.service.IAboutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/about")
public class AboutControllerImpl implements IAboutController {

    @Autowired
    private IAboutService aboutService;

    @PostMapping("/admin")
    @Operation(
            summary = "Yeni Hakkımda Ekle",
            requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content =@Content(mediaType = "multipart/form-data",
                            schema =@Schema(implementation = AboutRequest.class)
                    )
            )

    )
    @Override
    public ResponseEntity<AboutResponse> createAbout(@ModelAttribute AboutRequest aboutRequest) {
        try{
            AboutResponse about = aboutService.createAbout(aboutRequest);
            return ResponseEntity.ok(about);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/admin/{id}")
    @Override
    public ResponseEntity<AboutResponse> updateAbout(@PathVariable int id,@ModelAttribute AboutRequest aboutRequest) {
            AboutResponse about = aboutService.updateAbout(id, aboutRequest);
            return ResponseEntity.ok(about);

    }

    @GetMapping("/")
    @Override
    public ResponseEntity<AboutResponse> getAbout() {
        AboutResponse aboutResponse = this.aboutService.getAbout();
        return ResponseEntity.ok(aboutResponse);
    }
}
