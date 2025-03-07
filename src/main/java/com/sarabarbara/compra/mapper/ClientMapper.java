package com.sarabarbara.compra.mapper;

import com.sarabarbara.compra.dto.ClientCreateDTO;
import lombok.Builder;

import java.time.LocalDate;

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

}
