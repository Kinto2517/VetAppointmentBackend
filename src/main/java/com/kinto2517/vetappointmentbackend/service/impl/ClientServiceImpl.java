package com.kinto2517.vetappointmentbackend.service.impl;

import com.kinto2517.vetappointmentbackend.dto.*;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.Pet;
import com.kinto2517.vetappointmentbackend.mapper.ClientMapper;
import com.kinto2517.vetappointmentbackend.mapper.PetMapper;
import com.kinto2517.vetappointmentbackend.repository.ClientRepository;
import com.kinto2517.vetappointmentbackend.repository.PetRepository;
import com.kinto2517.vetappointmentbackend.request.PasswordChangeRequest;
import com.kinto2517.vetappointmentbackend.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PetRepository petRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             PetRepository petRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.petRepository = petRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return ClientMapper.INSTANCE.clientsToClientDTOs(clients);
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow();
        return ClientMapper.INSTANCE.clientToClientDTO(client);
    }

    @Override
    public ClientDTO saveClient(ClientSaveRequest clientSaveRequest) {
        Client client = ClientMapper.INSTANCE.clientSaveRequestToClient(clientSaveRequest);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.INSTANCE.clientToClientDTO(savedClient);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientMainSaveRequest clientMainSaveRequest) {
        Client client = clientRepository.findById(id).orElseThrow();
        client.setFirstName(clientMainSaveRequest.firstName());
        client.setLastName(clientMainSaveRequest.lastName());
        client.setPhoneNumber(clientMainSaveRequest.phoneNumber());

        Client savedClient = clientRepository.save(client);
        return ClientMapper.INSTANCE.clientToClientDTO(savedClient);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client with id " + id + " does not exist");
        }
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deletePet(Long id, Long petId) {
        Client client = clientRepository.findById(id).orElseThrow();
        Pet pet = client.getPets().stream().filter(p -> p.getId().equals(petId)).findFirst().orElseThrow();
        client.getPets().remove(pet);
        clientRepository.save(client);
        petRepository.deleteById(petId);
    }

    @Override
    @Transactional
    public PetDTO updatePet(Long id, Long petId, PetSaveRequest petSaveRequest) {
        Client client = clientRepository.findById(id).orElseThrow();
        Pet pet = client.getPets().stream().filter(p -> p.getId().equals(petId)).findFirst().orElseThrow();

        pet.setName(petSaveRequest.name());
        pet.setAge(petSaveRequest.age());
        pet.setBreed(petSaveRequest.breed());
        pet.setAllergies(petSaveRequest.allergies());
        pet.setBehavioralNotes(petSaveRequest.behavioralNotes());
        pet.setColor(petSaveRequest.color());
        pet.setCurrentHealthStatus(petSaveRequest.currentHealthStatus());
        pet.setDateOfBirth(petSaveRequest.dateOfBirth());
        pet.setDietaryInformation(petSaveRequest.dietaryInformation());
        pet.setGender(petSaveRequest.gender());
        pet.setInsuranceInformation(petSaveRequest.insuranceInformation());
        pet.setMedicalHistory(petSaveRequest.medicalHistory());
        pet.setMicrochipInformation(petSaveRequest.microchipInformation());
        pet.setSpecies(petSaveRequest.species());
        pet.setWeight(petSaveRequest.weight());

        clientRepository.save(client);
        petRepository.save(pet);
        return PetMapper.INSTANCE.petToPetDTO(pet);
    }

    @Override
    @Transactional
    public PetDTO addPet(Long id, PetSaveRequest petSaveRequest) {
        Client client = clientRepository.findById(id).orElseThrow();
        Pet pet = PetMapper.INSTANCE.petSaveRequestToPet(petSaveRequest);
        pet.setOwner(client);
        client.getPets().add(pet);
        clientRepository.save(client);
        petRepository.save(pet);
        return PetMapper.INSTANCE.petToPetDTO(pet);
    }

    @Override
    @Transactional
    public List<PetDTO> getClientPets(Long id) {
        Client client = clientRepository.findById(id).orElseThrow();
        List<Pet> pets = new ArrayList<>(client.getPets());
        return PetMapper.INSTANCE.petsToPetDTOs(pets);
    }

    @Override
    @Transactional
    public void addProfilePicturePet(Long id, Long petId, MultipartFile file) throws IOException {
        Client client = clientRepository.findById(id).orElseThrow();
        Pet pet = client.getPets().stream().filter(p -> p.getId().equals(petId)).findFirst().orElseThrow();
        pet.setPhotos(file.getBytes());
        petRepository.save(pet);
    }

    @Override
    @Transactional
    public byte[] getProfilePicturePet(Long id, Long petId) {
        Client client = clientRepository.findById(id).orElseThrow();
        Pet pet = client.getPets().stream().filter(p -> p.getId().equals(petId)).findFirst().orElseThrow();
        return pet.getPhotos();
    }

    @Override
    public void changePassword(PasswordChangeRequest changePasswordRequest, Principal principal) {
        Client client = (Client) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), client.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getNewPasswordConfirmation())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        client.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        clientRepository.save(client);
    }


}
