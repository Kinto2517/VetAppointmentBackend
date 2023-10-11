package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.*;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.service.ClientService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(VetDoctorController.class);

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Update Current Client
     **/

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientDTO> updateCleint(@PathVariable Long id,
                                                  @RequestBody ClientMainSaveRequest clientMainSaveRequest,
                                                  Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(clientService.updateClient(id, clientMainSaveRequest));
    }


    /** PETS - CLIENT **/

    /**
     * Get Current Client Pets
     */

    @GetMapping("/{id}/pets")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<PetDTO>> getClientPets(@PathVariable Long id,
                                                      Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(clientService.getClientPets(id));
    }

    /**
     * Add Pet to Current Client
     */

    @PostMapping("/{id}/add-pet")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<PetDTO> addPet(@PathVariable Long id,
                                         @RequestBody PetSaveRequest petSaveRequest,
                                         Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(clientService.addPet(id, petSaveRequest));
    }

    /**
     * Update Current Client Pet
     */

    @PutMapping("/{id}/update-pet/{petId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<PetDTO> updatePet(@PathVariable Long id,
                                            @PathVariable Long petId,
                                            @RequestBody PetSaveRequest petSaveRequest,
                                            Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(clientService.updatePet(id, petId, petSaveRequest));
    }

    /**
     * Delete Current Client Pet
     */

    @DeleteMapping("/{id}/delete-pet/{petId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> deletePet(@PathVariable Long id,
                                          @PathVariable Long petId,
                                          Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        clientService.deletePet(id, petId);

        return ResponseEntity.ok().build();
    }

    /** Profile-Picture Pet **/
    @PutMapping("/{id}/add-profile-picture-pet/{petId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addProfilePicturePet(@PathVariable Long id,
                                                    @PathVariable Long petId,
                                                    @RequestParam("file") MultipartFile file,
                                                    Authentication authentication) throws IOException {


        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());


        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        if (!isValidImage(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Image");
        }

        clientService.addProfilePicturePet(id, petId, file);

        return ResponseEntity.status(HttpStatus.OK).body("Profile picture updated successfully");
    }

    private boolean isValidImage(MultipartFile file) {
        try {
            return Objects.requireNonNull(file.getContentType()).startsWith("image/") &&
                    (Objects.requireNonNull(file.getContentType()).endsWith("png") ||
                            file.getContentType().endsWith("jpg") ||
                            file.getContentType().endsWith("jpeg"));
        } catch (Exception e) {
            return false;
        }

    }

    @GetMapping("/{id}/get-profile-picture-pet/{petId}")
    public ResponseEntity<byte[]> getProfilePicturePet(@PathVariable Long id,
                                                       @PathVariable Long petId,
                                                       Authentication authentication) {

        Client authenticatedClient = (Client) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedClient.getUsername());

        if (!authenticatedClient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(clientService.getProfilePicturePet(id, petId));
    }


}
