package com.sarabarbara.compra.responses.item;

import com.sarabarbara.compra.dto.items.ItemUpdateDTO;
import lombok.*;

/**
 * UpdateItemResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class UpdateItemResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The itemUpdate
     */

    private ItemUpdateDTO itemUpdate;

    /**
     * The message
     */

    private String message;

}
