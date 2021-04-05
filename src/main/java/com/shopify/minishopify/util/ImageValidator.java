package com.shopify.minishopify.util;

import org.apache.commons.codec.binary.Base64;

public class ImageValidator {
    public static boolean isValidImage(String image) {
        return Base64.isBase64(image);
    }
}
