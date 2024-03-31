package com.jaideep.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InputValidationFailedResponse {

    private int errorCode;
    private int input;
    private String message;

}
