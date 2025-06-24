package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.IAboutController;
import com.enesderin.portfolio.dto.AboutRequest;
import com.enesderin.portfolio.dto.AboutResponse;
import com.enesderin.portfolio.exception.BaseException;
import com.enesderin.portfolio.exception.ErrorMessage;
import com.enesderin.portfolio.exception.MessageType;
import com.enesderin.portfolio.model.RootEntity;
import com.enesderin.portfolio.service.IAboutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutControllerImpl extends RestBaseController implements IAboutController {

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
    public RootEntity<AboutResponse> createAbout(@ModelAttribute @Valid AboutRequest aboutRequest) {
        try{
            AboutResponse about = aboutService.createAbout(aboutRequest);
            return ok(about);
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON,null));
        }
    }

    @PutMapping("/admin/{id}")
    @Override
    public RootEntity<AboutResponse> updateAbout(@PathVariable int id,@ModelAttribute @Valid AboutRequest aboutRequest) {
            AboutResponse about = aboutService.updateAbout(id, aboutRequest);
            return ok(about);

    }

    @GetMapping()
    @Override
    public RootEntity<List<AboutResponse>> getAbout() {
        return ok(this.aboutService.getAbout());
    }
}
