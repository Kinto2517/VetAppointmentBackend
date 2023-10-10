package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.ClientSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.VetDoctorSaveRequest;
import com.kinto2517.vetappointmentbackend.request.AuthenticationRequest;
import com.kinto2517.vetappointmentbackend.response.AuthenticationResponse;
import com.kinto2517.vetappointmentbackend.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/clientregister")
    public ResponseEntity<AuthenticationResponse> vetDoctorRegister(@RequestBody ClientSaveRequest request){
        return ResponseEntity.ok(authenticationService.clientRegister(request));
    }

    @PostMapping("/vetdoctorregister")
    public ResponseEntity<AuthenticationResponse> clientRegister(@RequestBody VetDoctorSaveRequest request){
        return ResponseEntity.ok(authenticationService.vetDoctorRegister(request));
    }

    @PostMapping("/clientauthenticate")
    public ResponseEntity<AuthenticationResponse> clientAuthenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticateClient(request));
    }

    @PostMapping("/vetdoctorauthenticate")
    public ResponseEntity<AuthenticationResponse> vetDoctorAuthenticate(@RequestBody AuthenticationRequest request){
        logger.info("VetDoctor Authenticate");
        logger.info("Username: {}", request.getUsername());
        logger.info("Password: {}", request.getPassword());

        return ResponseEntity.ok(authenticationService.authenticateVetDoctor(request));
    }

    @PostMapping("/refresh-client-token")
    public void refreshClientToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshClientToken(request, response);
    }

    @PostMapping("/refresh-vetdoctor-token")
    public void refreshVetDoctorToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshVetDoctorToken(request, response);
    }
}
