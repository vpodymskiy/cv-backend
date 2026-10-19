package com.vlad.contact.contact_backend.dto;

import java.util.Map;

public record ApiResponse(String message, Map<String, String> errors) {

    public static ApiResponse of(String message) {
        return new ApiResponse(message, Map.of());
    }
}
