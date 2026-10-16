package com.vlad.contact.contact_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email String email,
        @Size(max = 30) String phone,
        @NotBlank @Size(max = 5000) String message
) {
}
