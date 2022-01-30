package com.cydrag.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.net.URI;
import java.net.URISyntaxException;

public final class Utils {

    public static URI parseUri(String uriPath) {
        URI uri;
        try {
            uri = new URI(uriPath);
        } catch (URISyntaxException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't parse URL: '" + uriPath + "'");
        }

        return uri;
    }
}
