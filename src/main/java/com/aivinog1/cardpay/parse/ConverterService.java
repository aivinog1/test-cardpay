package com.aivinog1.cardpay.parse;

import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;

import java.nio.file.Path;
import java.util.List;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
public interface ConverterService {

    List<Response> convert(Path file);

    SupportedFileType type();
}
