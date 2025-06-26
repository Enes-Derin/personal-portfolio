package com.enesderin.portfolio.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXİST("1001","Üzgünüz. Aradığınızı bulamadık"),
    GENERAL_EXCEPTİON("9999","Genel bir hata oluştu"),
    UNAUTHORIZED("1002","İşlem yetkiniz yok"),
    USERNAMEORPASSWORDINVALID("1003","Kullanıcı adı ve ya şifre hatalı"),
    ALREADY_EXIST("1004","Zaten Kayıt var");

    private String code;
    private String message;

    MessageType(String code, String message){
        this.code = code;
        this.message = message;
    }

}
