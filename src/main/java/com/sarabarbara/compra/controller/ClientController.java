package com.sarabarbara.compra.controller;

import com.sarabarbara.compra.dto.ClientCreateDTO;
import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.responses.CreateClientResponse;
import com.sarabarbara.compra.service.ClientService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sarabarbara.compra.mapper.ClientMapper.toClientCreateDTOMapper;

/**
 * ClientController class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<CreateClientResponse> createClient(@Validated @RequestBody Client client) {

        try {

            logger.info("Creating client started");

            Client createdCLient = clientService.createClient(client);

            ClientCreateDTO createClientDTO = toClientCreateDTOMapper(createdCLient.getName(),
                    createdCLient.getSurname(), createdCLient.getCompany(), createdCLient.getPosition(),
                    createdCLient.getAddress(), createdCLient.getZipCode(), createdCLient.getProvince(),
                    createdCLient.getPhoneNumber(), createdCLient.getBirthDate());

            logger.info("Creating client finished");

            logger.info("Client created successfully: {}", createClientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateClientResponse(true, createClientDTO,
                    "Client created successfully"));
        } catch (Exception e) {

            logger.error("Can't create the client: Some internal error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateClientResponse(false, null,
                            e.getMessage()));
        }

    }

}
