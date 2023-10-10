package com.kinto2517.vetappointmentbackend.mapper;

import com.kinto2517.vetappointmentbackend.dto.ClientDTO;
import com.kinto2517.vetappointmentbackend.dto.ClientSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO clientToClientDTO(Client client);
    Client clientSaveRequestToClient(ClientSaveRequest clientSaveRequest);
    List<ClientDTO> clientsToClientDTOs(List<Client> clients);

}
