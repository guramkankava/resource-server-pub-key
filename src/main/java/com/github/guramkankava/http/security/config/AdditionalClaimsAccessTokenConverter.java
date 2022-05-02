package com.github.guramkankava.http.security.config;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AdditionalClaimsAccessTokenConverter extends JwtAccessTokenConverter {

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    public AdditionalClaimsAccessTokenConverter (JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        var oAuth2Authentication = jwtAccessTokenConverter.extractAuthentication(map);
        oAuth2Authentication.setDetails(map);
        return oAuth2Authentication;
    }

    @Override
    protected Map<String, Object> decode(String token) {
        String errorMessage;
        try {
            Method methodDecode = JwtAccessTokenConverter.class.getDeclaredMethod("decode", String.class);
            methodDecode.setAccessible(true);
            Object invokeResponse = methodDecode.invoke(jwtAccessTokenConverter, token);
            if (invokeResponse instanceof Map) {
                return (Map<String, Object>) invokeResponse;
            }
            errorMessage = "Unsupported format of jwt claims";
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            errorMessage = e.getMessage();
        }
        throw new InvalidTokenException(errorMessage);
    }
}
