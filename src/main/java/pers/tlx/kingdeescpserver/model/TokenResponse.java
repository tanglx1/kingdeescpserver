package pers.tlx.kingdeescpserver.model;

import lombok.Data;

@Data
public class TokenResponse {
    private TokenData data;
    private String errorCode;
    private String message;
    private boolean status;

    @Data
    public static class TokenData {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private String scope;
        private long expires_in;
        private String id_token;
        private Long id_token_expires_in;
        private String language;
    }
} 