package com.vlad.contact.contact_backend.controller;

import com.vlad.contact.contact_backend.dto.ContactRequest;
import com.vlad.contact.contact_backend.exception.EmailSendingException;
import com.vlad.contact.contact_backend.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmailService emailService;

    @Test
    void validRequestSendsEmailAndReturns200() throws Exception {
        String body = """
                {"name":"Ana","email":"ana@example.com","phone":"600000000","message":"Hola"}
                """;

        mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Mensaje enviado"));

        verify(emailService).sendContactMessage(any(ContactRequest.class));
    }

    @Test
    void invalidEmailAndBlankFieldsReturn400WithoutSending() throws Exception {
        String body = """
                {"name":"","email":"not-an-email","message":""}
                """;

        mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.email").exists())
                .andExpect(jsonPath("$.errors.message").exists());

        verify(emailService, never()).sendContactMessage(any());
    }

    @Test
    void malformedJsonReturns400() throws Exception {
        mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content("{not json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void emailFailureReturns500() throws Exception {
        doThrow(new EmailSendingException("fallo", new RuntimeException()))
                .when(emailService).sendContactMessage(any());

        String body = """
                {"name":"Ana","email":"ana@example.com","message":"Hola"}
                """;

        mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists());
    }
}
