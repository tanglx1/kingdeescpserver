package pers.tlx.kingdeescpserver.model;

import lombok.Data;

@Data
public class TokenRequest {
    private String client_id;
    private String client_secret;
    private String username;
    private String accountId;
    private String nonce;
    private String timestamp;
    private String language;
} 