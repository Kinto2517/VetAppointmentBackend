package com.kinto2517.vetappointmentbackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.vetappointmentbackend.dto.ClientSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.VetDoctorSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.enums.Role;
import com.kinto2517.vetappointmentbackend.repository.ClientRepository;
import com.kinto2517.vetappointmentbackend.repository.VetDoctorRepository;
import com.kinto2517.vetappointmentbackend.request.AuthenticationRequest;
import com.kinto2517.vetappointmentbackend.response.AuthenticationResponse;
import com.kinto2517.vetappointmentbackend.security.JwtService;
import com.kinto2517.vetappointmentbackend.service.AuthenticationService;
import com.kinto2517.vetappointmentbackend.token.Token;
import com.kinto2517.vetappointmentbackend.token.TokenRepository;
import com.kinto2517.vetappointmentbackend.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final ClientRepository clientRepository;
    private final VetDoctorRepository vetDoctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse clientRegister(ClientSaveRequest request) {
        var client = Client.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.CLIENT)
                .build();
        var savedClient = clientRepository.save(client);
        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);
        saveClientToken(savedClient, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse vetDoctorRegister(VetDoctorSaveRequest request) {
        var vetDoctor = VetDoctor
                .builder()
                .fullName(request.fullName())
                .phoneNumber(request.phoneNumber())
                .city(request.city())
                .description(request.description())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.VETDOCTOR)
                .email(request.email())
                .build();
        var savedVetDoctor = vetDoctorRepository.save(vetDoctor);
        var jwtToken = jwtService.generateToken(vetDoctor);
        var refreshToken = jwtService.generateRefreshToken(vetDoctor);
        saveVetDoctorToken(savedVetDoctor, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticateClient(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var client = clientRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);
        revokeAllClientTokens(client);
        saveClientToken(client, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticateVetDoctor(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var vetDoctor = vetDoctorRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(vetDoctor);
        var refreshToken = jwtService.generateRefreshToken(vetDoctor);
        revokeAllVetDoctorTokens(vetDoctor);
        saveVetDoctorToken(vetDoctor, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveClientToken(Client client, String jwtToken) {
        var token = Token.builder()
                .user(client)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveVetDoctorToken(VetDoctor vetDoctor, String jwtToken) {
        var token = Token.builder()
                .user(vetDoctor)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    private void revokeAllClientTokens(Client client) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(client.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void revokeAllVetDoctorTokens(VetDoctor vetDoctor) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(vetDoctor.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshVetDoctorToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var vetDoctor = this.vetDoctorRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, vetDoctor)) {
                var accessToken = jwtService.generateToken(vetDoctor);
                revokeAllVetDoctorTokens(vetDoctor);
                saveVetDoctorToken(vetDoctor, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void refreshClientToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var client = this.clientRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, client)) {
                var accessToken = jwtService.generateToken(client);
                revokeAllClientTokens(client);
                saveClientToken(client, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
