package com.sarabarbara.compra.responses.clients;

import com.sarabarbara.compra.dto.clients.ClientUpdateDTO;
import lombok.*;

/**
 * UpdateClientResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 08/03/2025
 */

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class UpdateClientResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The user
     */

    private ClientUpdateDTO user;

    /**
     * The message
     */

    private String message;

}
