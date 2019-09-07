package com.aivinog1.cardpay.convert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@RequiredArgsConstructor
public enum SupportedFileType {
    JSON(APPLICATION_JSON_VALUE, "json"), CSV("text/csv", "csv");

    @Getter
    private final String mimeType;
    @Getter
    private final String extension;

    public static SupportedFileType getByMimeType(String mimeType) {
        return matchByPredicate(supportedFileType -> supportedFileType.getMimeType().equalsIgnoreCase(mimeType));
    }

    public static SupportedFileType getByExtension(String extension) {
        return matchByPredicate(supportedFileType -> supportedFileType.getExtension().equalsIgnoreCase(extension));
    }

    private static SupportedFileType matchByPredicate(final Predicate<SupportedFileType> predicate) {
        for (final SupportedFileType supportedFileType: values()) {
            if (predicate.test(supportedFileType)) {
                return supportedFileType;
            }
        }

        throw new IllegalArgumentException(String.format("Can't find File type. Supported is: %s", Arrays.toString(values())));
    }
}
