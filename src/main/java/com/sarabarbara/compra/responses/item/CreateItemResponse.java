package com.sarabarbara.compra.responses.item;

import com.sarabarbara.compra.dto.items.ItemCreateDTO;
import lombok.*;

/**
 * CreateItemResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreateItemResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The clientCreate
     */

    private ItemCreateDTO itemCreateDTO;

    /**
     * The message
     */

    private String message;
}
