package com.sarabarbara.compra.dto.clients;

import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * ClientSearchDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 07/03/2025
 */

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientSearchDTO {

    /**
     * The name;
     */

    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The surname;
     */

    @Size(min = 3, max = 45, message = "The surname must be between 3 and 45 characters")
    private String surname;

    /**
     * The company;
     */

    @Size(min = 3, max = 45, message = "The company must be between 3 and 45 characters")
    private String company;

}
