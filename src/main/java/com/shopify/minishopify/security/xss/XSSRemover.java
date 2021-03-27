package com.shopify.minishopify.security.xss;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;

public class XSSRemover {
    public static String removeXSS(String value) {
        if (value == null) {
            return null;
        }

        return Jsoup.clean(ESAPI.encoder()
                .canonicalize(value)
                .replaceAll("\0", ""), Whitelist.none());
    }
}
