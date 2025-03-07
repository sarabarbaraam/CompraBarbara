package com.sarabarbara.compra.service;

import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

}
