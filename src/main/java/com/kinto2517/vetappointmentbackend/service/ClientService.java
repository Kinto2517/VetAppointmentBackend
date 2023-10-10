package com.kinto2517.vetappointmentbackend.service;

import com.kinto2517.vetappointmentbackend.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClients();

    ClientDTO getClientById(Long id);

    ClientDTO saveClient(ClientSaveRequest clientSaveRequest);

    ClientDTO updateClient(Long id, ClientMainSaveRequest clientMainSaveRequest);

    void deleteClient(Long id);

    void deletePet(Long id, Long petId);

    PetDTO updatePet(Long id, Long petId, PetSaveRequest petSaveRequest);

    PetDTO addPet(Long id, PetSaveRequest petSaveRequest);

    List<PetDTO> getClientPets(Long id);

    void addProfilePicturePet(Long id, Long petId, MultipartFile file) throws IOException;

    byte[] getProfilePicturePet(Long id, Long petId);
}
