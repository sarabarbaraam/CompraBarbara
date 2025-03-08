package com.sarabarbara.compra.mapper;

import com.sarabarbara.compra.dto.ClientCreateDTO;
import com.sarabarbara.compra.dto.ClientSearchDTO;
import com.sarabarbara.compra.model.Client;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * ClientMapper class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@Builder
public class ClientMapper {

    private ClientMapper() {

    }

    public static ClientCreateDTO toClientCreateDTOMapper(String name, String surname, String company, String position,
                                                          String address, String zipCode, String province,
                                                          String phoneNumber, LocalDate birthDate) {

        return ClientCreateDTO.builder()
                .name(name)
                .surname(surname)
                .company(company)
                .position(position)
                .address(address)
                .zipCode(zipCode)
                .province(province)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .build();
    }

    public static List<ClientSearchDTO> toClientSearchDTOMapper(@NotNull List<Client> searchedClient) {

        return searchedClient.stream()
                .map(client -> ClientSearchDTO.builder()
                        .name(client.getName())
                        .surname(client.getSurname())
                        .company(client.getCompany())
                        .position(client.getPosition())
                        .address(client.getAddress())
                        .zipCode(client.getZipCode())
                        .province(client.getProvince())
                        .phoneNumber(client.getPhoneNumber())
                        .birthDate(client.getBirthDate())
                        .build())
                .toList();
    }

}
