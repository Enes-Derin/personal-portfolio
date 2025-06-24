package com.enesderin.portfolio.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private MessageType type;
    private String ofStatic;

    public String prepareErrorMessage(){
        StringBuilder message = new StringBuilder();
        message.append(type.getMessage());
        if(ofStatic != null){
            message.append(" : " + ofStatic);
        }
        return message.toString();
    }

}
