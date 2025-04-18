package com.example.demo.common.exception;

public class NullValueException extends BaseException {

    public NullValueException() {
        super("필수 값이 null입니다.");
    }
}