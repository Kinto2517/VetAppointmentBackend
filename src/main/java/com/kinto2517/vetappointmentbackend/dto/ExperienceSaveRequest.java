package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record ExperienceSaveRequest(String organization, String jobTitle, Date startDate, Date endDate, String description) {
}
