package com.sarabarbara.compra.controller;

import com.sarabarbara.compra.dto.clients.ClientCreateDTO;
import com.sarabarbara.compra.dto.clients.ClientSearchDTO;
import com.sarabarbara.compra.dto.clients.ClientUpdateDTO;
import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.responses.SearchResponse;
import com.sarabarbara.compra.responses.clients.CreateClientResponse;
import com.sarabarbara.compra.responses.clients.UpdateClientResponse;
import com.sarabarbara.compra.service.ClientService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sarabarbara.compra.mapper.ClientMapper.*;

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

    /**
     * The creation client controller
     *
     * @param client the client to create
     *
     * @return the created client
     */

    @PostMapping("/create")
    public ResponseEntity<CreateClientResponse> createClient(@NotNull @Validated @RequestBody Client client) {

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

            logger.error("Can't create the client: Some internal error occurred {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateClientResponse(false, null,
                            e.getMessage()));
        }

    }

    /**
     * The client list controller
     *
     * @param page the page
     * @param size the size of the page
     *
     * @return the list of all clients
     */

    @RequestMapping
    public ResponseEntity<SearchResponse<Client>> clientList(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int size) {

        try {
            logger.info("List of clients started");

            List<Client> clientList = clientService.clientList(page - 1, size);
            int totalPages = (int) Math.ceil((double) clientList.size() / size);

            return ResponseEntity.status(HttpStatus.OK).body(new SearchResponse<>(
                    clientList, clientList.size(), page, totalPages, "Successful"));

        } catch (Exception e) {

            logger.error("Can't get the client list. Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0,
                            e.getMessage()));
        }
    }

    /**
     * The search client controller
     *
     * @param name        the name of the client
     * @param surname     the surname of the client
     * @param company     the company of the client
     * @param position    the position of the client
     * @param zipCode     the zip code of the client
     * @param province    the province of the client
     * @param phoneNumber the phone number of the client
     * @param page        the page
     * @param size        the size of the page
     *
     * @return the searched client
     */

    @GetMapping("/search")
    public ResponseEntity<SearchResponse<ClientSearchDTO>> searchClient(@RequestParam(required = false) String name,
                                                                        @RequestParam(required = false) String surname,
                                                                        @RequestParam(required = false) String company,
                                                                        @RequestParam(required = false) String position,
                                                                        @RequestParam(required = false) String zipCode,
                                                                        @RequestParam(required = false) String province,
                                                                        @RequestParam(required = false) String phoneNumber,
                                                                        @RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {

        try {

            logger.info("Searching client started");

            List<Client> searchedClient = clientService.searchClient(name, surname, company, position, zipCode,
                    province, phoneNumber, page, size);

            if (searchedClient.isEmpty()) {

                logger.info("No clients found");

                logger.info("Searching client finished without content");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<ClientSearchDTO> clientSearchDTO = toClientSearchDTOMapper(searchedClient);

            int totalPage = (int) Math.ceil((double) searchedClient.size() / size);

            SearchResponse<ClientSearchDTO> response = new SearchResponse<>(
                    clientSearchDTO, searchedClient.size(), page, totalPage, "Successfully");

            logger.info("Clients found:");
            clientSearchDTO.forEach(client -> logger.info("  - name: {}, surname: {}, company: {}, " +
                            "position: {}, zipCode: {}, province: {}, phoneNumber: {}",
                    client.getName(), client.getSurname(), client.getCompany(), client.getPosition(),
                    client.getZipCode(), client.getProvince(), client.getPhoneNumber()));

            logger.info("Searching user finished");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            logger.error("Can't search user: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0,
                            e.getMessage()));
        }
    }

    /**
     * The update client controller
     *
     * @param phoneNumber the phoneNumber of the client to search for
     * @param client      the client's data
     *
     * @return the updated client
     */

    @PatchMapping("/{phoneNumber}/update")
    public ResponseEntity<UpdateClientResponse> updateClient(@PathVariable String phoneNumber,
                                                             @RequestBody Client client) {

        try {

            logger.info("Updating user started");

            Client updatedClient = clientService.updateClient(phoneNumber, client);

            ClientUpdateDTO clientUpdateDTO = toClientUpdateDTOMapper(updatedClient);

            logger.info("User updated successfully: {}", clientUpdateDTO);

            logger.info("Updating user finished");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UpdateClientResponse(true, clientUpdateDTO, "Client updated successfully"));

        } catch (Exception e) {

            logger.error("Can't update user: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UpdateClientResponse(false, null, e.getMessage()));
        }
    }

}
