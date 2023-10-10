package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record EducationSaveRequest(String institution, String degree, String fieldOfStudy, Date startDate, Date graduationDate, String description) {
}