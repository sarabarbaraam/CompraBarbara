package com.sarabarbara.compra.service;

import com.sarabarbara.compra.exceptions.client.ClientNotFoundException;
import com.sarabarbara.compra.exceptions.client.ClientValidateException;
import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ClientService class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@Service
@AllArgsConstructor
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private ClientRepository clientRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Method to create a client
     *
     * @param client the client's data
     *
     * @return the created client
     *
     * @throws ClientValidateException the {@link ClientValidateException}
     */

    public Client createClient(Client client) throws ClientValidateException {

        logger.info("Creating client...");
        validateNewClient(client);

        logger.info("Client created successfully");
        return clientRepository.save(client);
    }

    /**
     * Method to get the list of all clients
     *
     * @param page the page to search
     * @param size the size of the page
     *
     * @return the list of all clients
     */

    public List<Client> clientList(int page, int size) {

        logger.info("Client list. Page {}, size {}", page, size);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Client> pageResult = clientRepository.findAll(pageRequest);

        List<Client> clientList = pageResult.getContent();

        logger.info("Clients list: {}. ", clientList);
        return clientList;
    }

    /**
     * Method to search a client
     *
     * @param name        the name of the client
     * @param surname     the surname of the client
     * @param company     the company of the client
     * @param position    the position of the client
     * @param zipCode     the zip code of the client
     * @param province    the province of the client
     * @param phoneNumber the phone number of the client
     * @param page        the page to search
     * @param size        the size of the page
     *
     * @return the searched client
     */

    public List<Client> searchClient(String name, String surname, String company, String position, String zipCode,
                                     String province, String phoneNumber, int page, int size) {

        logger.info("Searching client...");

        if (page < 1) {
            throw new IllegalArgumentException("Page index must be at least 1");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<Client> searchedClient = clientRepository.searchClients(name, surname, company, position, zipCode,
                province, phoneNumber, pageRequest);

        logger.info("Clients found: {}", searchedClient.getContent());
        return searchedClient.getContent();

    }

    /**
     * Method to update client's data
     *
     * @param phoneNumber the phone number of the client
     * @param newInfo     the new info to be updated
     *
     * @return the updated client
     *
     * @throws ClientNotFoundException the {@link ClientNotFoundException}
     */

    public Client updateClient(String phoneNumber, Client newInfo) throws ClientNotFoundException {

        logger.info("Updating client with telephone number {}", phoneNumber);
        Client optionalClient = clientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ClientNotFoundException("Can't update client: Client not found"));

        logger.info("New client info: {}", newInfo);

        // ignores the id field
        modelMapper.typeMap(Client.class, Client.class).addMappings(mapper -> mapper.skip(Client::setIdClient));

        // ignores the null fields
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        /* copies the values of the Client object (newInfo, any non-null field)
        to the existingClient object. */

        modelMapper.map(newInfo, optionalClient);

        logger.info("Updating client {} {}...", optionalClient.getName(), optionalClient.getSurname());
        clientRepository.save(optionalClient);


        logger.info("Client {} updated successfully", optionalClient);
        return optionalClient;
    }

    /**
     * Method to delete a client
     *
     * @param phoneNumber the phone number of the client
     *
     * @throws ClientNotFoundException the {@link ClientNotFoundException}
     */

    public void deleteClient(String phoneNumber) throws ClientNotFoundException {

        Client optionalClient = clientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ClientNotFoundException("Can't update client: Client not found"));

        Long id = optionalClient.getIdClient();

        logger.info("Deleting client: {}", optionalClient);
        clientRepository.deleteById(id);

        logger.info("Client with id {} (phone number: {}) has been deleted successfully.", id, phoneNumber);
    }

    /**
     * Method to see the sheet of the client
     * In this method it searched the id of the client to see their sheet
     *
     * @param idClient the id of the client
     *
     * @return the id of the client
     */

    public Client clientSheet(Long idClient) {

        logger.info("Client sheet for id {}", idClient);

        Optional<Client> client = clientRepository.findById(idClient);

        return client.orElse(null);

    }

    // Complementary methods

    /**
     * Validate the info of new client is correct
     *
     * @param client the client
     */

    private void validateNewClient(@NonNull Client client) {

        logger.info("Validating client...");
        companyValidator(client.getCompany());
    }

    /**
     * Validates if the company is taken
     *
     * @param company the company
     */

    private void companyValidator(String company) {

        Optional<Client> optionalCompany = clientRepository.findByCompany(company);

        if (optionalCompany.isPresent()) {

            logger.error("The company {} is already taken.", company);
            throw new ClientValidateException("The company " + company + " is already taken.");
        }

        logger.info("The company {} is available", company);
    }


}