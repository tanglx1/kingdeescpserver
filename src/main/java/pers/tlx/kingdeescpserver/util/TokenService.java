package pers.tlx.kingdeescpserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pers.tlx.kingdeescpserver.model.TokenRequest;
import pers.tlx.kingdeescpserver.model.TokenResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final String TOKEN_URL = "https://feature.kingdee.com:1026/feature_sit_scm/kapi/oauth2/getToken";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final RestTemplate restTemplate = new RestTemplate();
    private String cachedToken;
    private LocalDateTime tokenExpiryTime;

    @Value("${kingdee.client.id:mcpserverdemo}")
    private String clientId;

    @Value("${kingdee.client.secret:jiwejiJI23IJLKJAjl!%^}")
    private String clientSecret;

    @Value("${kingdee.username:timothy}")
    private String username;

    @Value("${kingdee.account.id:1356502410930947072}")
    private String accountId;

    public synchronized String getToken() {
        if (cachedToken != null && tokenExpiryTime != null && LocalDateTime.now().isBefore(tokenExpiryTime)) {
            return cachedToken;
        }

        TokenRequest request = new TokenRequest();
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);
        request.setUsername(username);
        request.setAccountId(accountId);
        request.setNonce(UUID.randomUUID().toString());
        request.setTimestamp(LocalDateTime.now().format(TIMESTAMP_FORMATTER));
        request.setLanguage("zh_CN");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TokenRequest> entity = new HttpEntity<>(request, headers);

        try {
            TokenResponse response = restTemplate.postForObject(TOKEN_URL, entity, TokenResponse.class);
            if (response != null && response.isStatus() && response.getData() != null) {
                cachedToken = response.getData().getAccess_token();
                // 设置token过期时间为1小时
                tokenExpiryTime = LocalDateTime.now().plusHours(1);
                return cachedToken;
            }
            logger.error("Failed to get token: {}", response != null ? response.getMessage() : "Unknown error");
        } catch (Exception e) {
            logger.error("Error getting token", e);
        }
        return null;
    }
} 