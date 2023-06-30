package org.example;

import com.google.gson.JsonObject;

public record BaseResponse(JsonObject data, Pagination pagination) {
}
