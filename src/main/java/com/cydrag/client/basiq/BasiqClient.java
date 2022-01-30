package com.cydrag.client.basiq;

import com.cydrag.client.basiq.response.TokenResponse;
import com.cydrag.client.basiq.response.TransactionsResponse;
import com.cydrag.config.Config;
import com.cydrag.config.Constants;
import com.cydrag.util.FormUrlEncodedConverter;
import com.cydrag.util.Timer;
import com.cydrag.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class BasiqClient {

    private static final Logger log = LoggerFactory.getLogger(BasiqClient.class);

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Config config;

    private TokenResponse cachedToken;

    @Autowired
    public BasiqClient(Config config) {
        this.httpClient = HttpClient.newBuilder().build();
        this.objectMapper = new ObjectMapper();
        this.config = config;
        this.cachedToken = null;
    }

    private synchronized void checkAuthenticationToken() {
        if (this.cachedToken == null) {
            log.info("Authentication token for Basiq services is expired. Requesting new authentication token...");
            this.cachedToken = this.getAuthenticationToken(Constants.BASIQ_VERSION_VALUE);
        }
        else {
            log.info("Using cached token...");
        }
    }

    private TokenResponse getAuthenticationToken(String version) {
        FormUrlEncodedConverter formUrlEncodedConverter = new FormUrlEncodedConverter();
        formUrlEncodedConverter.addProperty(Constants.BASIQ_SCOPE_BODY_PROPERTY, Constants.BASIQ_SERVER_SCOPE);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(Utils.parseUri(Constants.BASIQ_TOKEN_URL))
                .header(Constants.BASIQ_VERSION_HEADER, version)
                .header(HttpHeaders.AUTHORIZATION, Constants.BASIC_AUTHENTICATION + config.getBasiqApiKey())
                .header(HttpHeaders.CONTENT_TYPE, Constants.X_WWW_FORM_URLENCODED)
                .POST(HttpRequest.BodyPublishers.ofByteArray(formUrlEncodedConverter.toByteArray()))
                .build();

        HttpResponse<String> response;

        try {
           response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while sending request to Basiq Authorization service.");
        }

        TokenResponse tokenResponse;

        try {
            tokenResponse = objectMapper.readValue(response.body(), TokenResponse.class);
        } catch (JsonProcessingException ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while parsing Basiq authentication response body.");
        }

        log.info("Successfully fetched new authentication token for Basiq services.");
        Timer.setTimeout(() -> {
            log.info("Executing background task - Clearing Basiq authentication token...");
            cachedToken = null;
        }, (tokenResponse.getExpiresIn() - 300) * 1000);

        return tokenResponse;
    }

    public TransactionsResponse getTransactionsByUserId(String userId) {
        this.checkAuthenticationToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(Utils.parseUri(Constants.getBasiqUserTransactionsUrl(userId)))
                .header(HttpHeaders.AUTHORIZATION, Constants.BEARER_AUTHENTICATION + this.cachedToken.getAccessToken())
                .GET()
                .build();

        HttpResponse<String> response;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while requesting Basiq Transaction service for user " + userId);
        }

        TransactionsResponse transactionsResponse;

        try {
            transactionsResponse = objectMapper.readValue(response.body(), TransactionsResponse.class);
        } catch (JsonProcessingException ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while parsing Basiq transactions response body.");
        }

        return transactionsResponse;
    }
}
