package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.RatingDTO;
import com.kinto2517.vetappointmentbackend.dto.RatingSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final RatingService ratingService;
    private static final Logger logger = LoggerFactory.getLogger(VetDoctorController.class);

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{clientid}/create/{vetdoctorid}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<RatingDTO> createRating(@PathVariable Long clientid,
                                                  @PathVariable Long vetdoctorid,
                                                  @RequestBody RatingSaveRequest ratingSaveRequest,
                                                  Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(clientid)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(ratingService.createRating(clientid, vetdoctorid, ratingSaveRequest));
    }

    @GetMapping("/vetdoctor/{vetdoctorid}")
    public ResponseEntity<List<RatingDTO>> getRatingsByVetDoctorId(@PathVariable Long vetdoctorid) {
        return ResponseEntity.ok(ratingService.getRatingsByVetDoctorId(vetdoctorid));
    }


}
