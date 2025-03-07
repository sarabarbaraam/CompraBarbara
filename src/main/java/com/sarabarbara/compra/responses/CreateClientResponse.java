package com.sarabarbara.compra.responses;

import com.sarabarbara.compra.dto.ClientCreateDTO;
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

    private boolean success;

    private ClientCreateDTO clientCreate;

    private String message;
}
