package com.sarabarbara.compra.responses.clients;

import com.sarabarbara.compra.dto.clients.ClientCreateDTO;
import lombok.*;

/**
 * CreateClientResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreateClientResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The clientCreate
     */

    private ClientCreateDTO clientCreate;

    /**
     * The message
     */

    private String message;

}
