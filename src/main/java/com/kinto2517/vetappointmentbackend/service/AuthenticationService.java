package com.kinto2517.vetappointmentbackend.service;


import com.kinto2517.vetappointmentbackend.dto.ClientSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.VetDoctorSaveRequest;
import com.kinto2517.vetappointmentbackend.request.AuthenticationRequest;
import com.kinto2517.vetappointmentbackend.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse clientRegister(ClientSaveRequest request);

    AuthenticationResponse vetDoctorRegister(VetDoctorSaveRequest request);

    AuthenticationResponse authenticateVetDoctor(AuthenticationRequest request);

    AuthenticationResponse authenticateClient(AuthenticationRequest request);

    void refreshClientToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void refreshVetDoctorToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
