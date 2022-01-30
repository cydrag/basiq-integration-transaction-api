package com.cydrag.config;

public final class Constants {

    private Constants() {}

    // Utils configuration
    public static final String BASIC_AUTHENTICATION = "Basic ";
    public static final String BEARER_AUTHENTICATION = "Bearer ";
    public static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    // Basiq configuration
    public static final String BASIQ_TOKEN_URL = "https://au-api.basiq.io/token";
    private static final String BASIQ_USER_TRANSACTIONS_URL = "https://au-api.basiq.io/users/%s/transactions";

    public static final String BASIQ_VERSION_HEADER = "basiq-version";
    public static final String BASIQ_SCOPE_BODY_PROPERTY = "scope";

    public static final String BASIQ_VERSION_VALUE = "2.0";
    public static final String BASIQ_SERVER_SCOPE = "SERVER_SCOPE";

    public static final String BASIQ_USER_ID = "34bc08aa-5888-4078-879f-68b5cac9efac";

    public static String getBasiqUserTransactionsUrl(String userId) {
        return String.format(BASIQ_USER_TRANSACTIONS_URL, userId);
    }
}
