package com.aivinog1.cardpay.exception.file;

import com.aivinog1.cardpay.exception.AppException;

public class FileIsNotReadableException extends AppException {

    public FileIsNotReadableException(String path) {
        super(String.format("File: %s is not readable.", path));
    }
}
