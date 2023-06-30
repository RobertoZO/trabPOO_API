package org.example;

public class Main {
    public static void main(String[] args) {
        PaymentsSispag paymentsSispag = new PaymentsSispag("https://devportal.itau.com.br", "/sandboxapi/itau-ep9-gtw-sispag-ext/v1/pagamentos_sispag", "/api/jwt");
        paymentsSispag.payments();
    }
}