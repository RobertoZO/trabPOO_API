package org.example;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PaymentsSispag {
    private String sourceBase;
    private String sourceRoute;
    private String sourceRoute2;
    private Token token;
    private BodyAuth bodyAuth;

    public PaymentsSispag (String sourceBase, String sourceRoute, String sourceRoute2){
        this.sourceBase = sourceBase;
        this.sourceRoute = sourceRoute;
        this.sourceRoute2 = sourceRoute2;
        token = new Token();
        token.setPaymentsSispag(this);
        bodyAuth = new BodyAuth();
    }

    public BodyAuth getBodyAuth() {
        return bodyAuth;
    }

    public void payments(){
        String responseBody = getResponseBody(sourceBase + sourceRoute);
        BaseResponse baseResponse = (BaseResponse) getObject(responseBody, BaseResponse.class);
        PaymentBody paymentBody = (PaymentBody) getObject(baseResponse.data().toString(), PaymentBody.class);

        for (int i = 0; i < paymentBody.itens().length; i ++){
            System.out.println(paymentBody.itens()[i]);
        }
    }
    public String sendRequest(HttpClient client, HttpRequest request){
        String responseBody = null;
        try{
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            responseBody = response.body();
        }catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            System.out.println("Ocorreu algum erro durente a execução do código :)");
            System.exit(1);
        }

        return responseBody;
    }

    public String getResponseBody(String url){
        String sourceRoute = sourceBase + sourceRoute2;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("x-itau-apikey", bodyAuth.getId())
                .header("x-sandbox-token", token.getToken(sourceRoute))
                .GET()
                .build();

        return sendRequest(client, request);
    }

    public Object getObject(String responseBody, Type objClass){
        Object convertedObject = null;
        try{
            convertedObject = new GsonBuilder()
                    .create()
                    .fromJson(responseBody, objClass);
        }catch (JsonParseException exception){
            exception.printStackTrace();
            System.out.println("Ocorreu algum erro durente a execução do código :)");
            System.exit(1);
        }
        return convertedObject;
    }
}
