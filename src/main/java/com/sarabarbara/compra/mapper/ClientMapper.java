package com.sarabarbara.compra.mapper;

import com.sarabarbara.compra.dto.clients.ClientCreateDTO;
import com.sarabarbara.compra.dto.clients.ClientDTO;
import com.sarabarbara.compra.dto.clients.ClientSearchDTO;
import com.sarabarbara.compra.dto.clients.ClientUpdateDTO;
import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.sheets.ClientSheet;
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

    /**
     * The private constructor
     */

    private ClientMapper() {

    }

    /**
     * The Client to ClientCreateDTO mapper
     *
     * @param name        the name of the client
     * @param surname     the surname of the client
     * @param company     the company of the client
     * @param position    the position of the client
     * @param address     the address of the client
     * @param zipCode     the zip code of the client
     * @param province    the province of the client
     * @param phoneNumber the phone number of the client
     * @param birthDate   the birthdate of the client
     *
     * @return the ClientCreateDTO
     */

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

    /**
     * The Client to ClientDTO Mapper
     *
     * @param clientList the client list
     *
     * @return the ClientDTO
     */

    public static List<ClientDTO> toClientDTOMapper(@NotNull List<Client> clientList) {

        return clientList.stream()
                .map(client -> ClientDTO.builder()
                        .idClient(client.getIdClient())
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

    /**
     * The Client to ClientSearchDTO mapper
     *
     * @param searchedClient the searched client
     *
     * @return the ClientSearchDTO
     */

    public static List<ClientSearchDTO> toClientSearchDTOMapper(@NotNull List<Client> searchedClient) {

        return searchedClient.stream()
                .map(client -> ClientSearchDTO.builder()
                        .name(client.getName())
                        .surname(client.getSurname())
                        .company(client.getCompany())
                        .build())
                .toList();
    }

    /**
     * The Client to ClientUpdateDTO mapper
     *
     * @param updateClient the client to be updated
     *
     * @return the ClientUpdateDTO
     */

    public static ClientUpdateDTO toClientUpdateDTOMapper(@NotNull Client updateClient) {

        return ClientUpdateDTO.builder()
                .name(updateClient.getName())
                .surname(updateClient.getSurname())
                .company(updateClient.getCompany())
                .position(updateClient.getPosition())
                .address(updateClient.getAddress())
                .zipCode(updateClient.getZipCode())
                .province(updateClient.getProvince())
                .phoneNumber(updateClient.getPhoneNumber())
                .birthDate(updateClient.getBirthDate())
                .build();
    }

    /**
     * The Client to ClientSheet Mapper
     *
     * @param client the client
     *
     * @return the sheet of the client
     */

    public static ClientSheet toClientSheetMapper(@NotNull Client client) {

        return ClientSheet.builder()
                .name(client.getName())
                .surname(client.getSurname())
                .company(client.getCompany())
                .position(client.getPosition())
                .address(client.getAddress())
                .zipCode(client.getZipCode())
                .province(client.getProvince())
                .phoneNumber(client.getPhoneNumber())
                .birthDate(client.getBirthDate())
                .build();
    }

}
