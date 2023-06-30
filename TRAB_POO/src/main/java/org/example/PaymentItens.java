package org.example;

public record PaymentItens(String id_pagamento,
                           String status,
                           String data_pagamento,
                           String valor_pagamento) {
}