package com.kinto2517.vetappointmentbackend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    VETDOCTOR_READ("vetdoctor:read"),
    VETDOCTOR_UPDATE("vetdoctor:update"),
    VETDOCTOR_CREATE("vetdoctor:create"),
    VETDOCTOR_DELETE("vetdoctor:delete"),

    CLIENT_READ("client:read"),
    CLIENT_UPDATE("client:update"),
    CLIENT_CREATE("client:create"),
    CLIENT_DELETE("client:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    ;

    @Getter
    private final String permission;
}
