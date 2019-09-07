package com.aivinog1.cardpay.exception.file;

import com.aivinog1.cardpay.exception.AppException;

public class FileNotFoundException extends AppException {
    public FileNotFoundException(String fileName) {
        super(String.format("File: %s doesn't exists.", fileName));
    }
}
