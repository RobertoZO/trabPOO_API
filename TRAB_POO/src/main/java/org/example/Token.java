package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class Token {
    private String access_token;
    private String token_type;
    private String expires_in;
    private Boolean active;
    private String scope;

    private transient PaymentsSispag paymentsSispag = null;

    public void setPaymentsSispag(PaymentsSispag paymentsSispag) {
        this.paymentsSispag = paymentsSispag;
    }

    public String getToken(String sourceRoute){
        if (paymentsSispag != null){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(sourceRoute))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(paymentsSispag.getBodyAuth().toString()))
                    .build();

            String responseBody = paymentsSispag.sendRequest(client, request);
            Token token = (Token) paymentsSispag.getObject(responseBody, Token.class);

            return token.access_token;
        }

        return null;
    }
}
