package com.sarabarbara.compra.service;

import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Client createClient(Client client) {

        logger.info("Creating client...");
        logger.info("Client created successfully");
        return clientRepository.save(client);
    }

    public List<Client> clientList(int page, int size) {

        logger.info("Client list. Page {}, size {}", page, size);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Client> pageResult = clientRepository.findAll(pageRequest);

        List<Client> clientList = pageResult.getContent();

        logger.info("Clients list: {}. ", clientList);
        return clientList;
    }

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

}
