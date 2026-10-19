package com.vlad.contact.contact_backend.controller;

import com.vlad.contact.contact_backend.dto.ApiResponse;
import com.vlad.contact.contact_backend.dto.ContactRequest;
import com.vlad.contact.contact_backend.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<ApiResponse> receiveMessage(@Valid @RequestBody ContactRequest request) {
        emailService.sendContactMessage(request);
        return ResponseEntity.ok(ApiResponse.of("Mensaje enviado"));
    }
}
