package com.sarabarbara.compra.service;

import com.sarabarbara.compra.exceptions.ClientNotFoundException;
import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sarabarbara.compra.constants.Constants.CLIENT_NOT_FOUND;

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
     */

    public Client createClient(Client client) {

        logger.info("Creating client...");
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

        Optional<Client> optionalClient = clientRepository.findByPhoneNumber(phoneNumber);

        logger.info("Updating user: {}", optionalClient);

        if (optionalClient.isPresent()) {

            Client existingClient = optionalClient.get();

            logger.info("New user info: {}", newInfo);

            // ignores the id field
            modelMapper.typeMap(Client.class, Client.class).addMappings(mapper -> mapper.skip(Client::setIdClient));

            // ignores the null fields
            modelMapper.getConfiguration().setSkipNullEnabled(true);

            /* copies the values of the Client object (newInfo, any non-null field)
            to the existingClient object. */
            modelMapper.map(newInfo, existingClient);

            logger.info("Updating user {} {}...", existingClient.getName(), existingClient.getSurname());
            clientRepository.save(existingClient);


            logger.info("User {} updated successfully", existingClient);
            return existingClient;
        }

        logger.error("User with phone number {} can't be updated: user not found", phoneNumber);
        throw new ClientNotFoundException("Can't update user: User not found");
    }

    /**
     * Method to delete a client
     *
     * @param phoneNumber the phone number of the client
     *
     * @throws ClientNotFoundException the {@link ClientNotFoundException}
     */

    public void deleteUser(String phoneNumber) throws ClientNotFoundException {

        Optional<Client> optionalClient = clientRepository.findByPhoneNumber(phoneNumber);

        if (optionalClient.isEmpty()) {

            logger.error(CLIENT_NOT_FOUND);
            throw new ClientNotFoundException(CLIENT_NOT_FOUND);
        }

        Long id = optionalClient.get().getIdClient();

        logger.info("Deleting client: {}", optionalClient);
        clientRepository.deleteById(id);

        logger.info("Client with id {} (phone number: {}) has been deleted successfully.", id, phoneNumber);
    }

}
