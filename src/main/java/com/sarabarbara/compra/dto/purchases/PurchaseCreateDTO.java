package com.sarabarbara.compra.dto.purchases;

import lombok.*;

/**
 * PurchaseCreateDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PurchaseCreateDTO {

    /**
     * The idClient;
     */

    private Long idClient;

    /**
     * The idItem;
     */

    private Long idItem;

    /**
     * The quantity;
     */

    private int quantity;

}
