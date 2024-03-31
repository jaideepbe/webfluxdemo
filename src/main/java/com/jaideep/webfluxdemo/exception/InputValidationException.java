package com.jaideep.webfluxdemo.exception;

public class InputValidationException extends RuntimeException{

    private static final String MSG = "Input should be between 10 and 20";
    private static final int ERROR_CODE = 100;
    private final int input;

    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }


}
