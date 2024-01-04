package com.mainproject.be28.global.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public final class UriCreator {
    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }

}
