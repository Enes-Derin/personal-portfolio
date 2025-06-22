package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.IAboutController;
import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import com.enesderin.portfolio.service.IAboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/about")
public class AboutControllerImpl implements IAboutController {

    @Autowired
    private IAboutService aboutService;

    @PostMapping("/admin")
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
