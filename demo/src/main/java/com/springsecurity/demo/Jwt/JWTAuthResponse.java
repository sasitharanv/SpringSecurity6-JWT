package com.springsecurity.demo.Jwt;


public class JWTAuthResponse {
    private String accessToken;

    public JWTAuthResponse() {
    }

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
