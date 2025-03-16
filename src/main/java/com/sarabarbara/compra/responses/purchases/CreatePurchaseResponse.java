package com.sarabarbara.compra.responses.purchases;

import com.sarabarbara.compra.dto.purchases.PurchaseCreateDTO;
import lombok.*;

/**
 * CreatePurchaseResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreatePurchaseResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The clientCreate
     */

    private PurchaseCreateDTO purchaseCreate;

    /**
     * The message
     */

    private String message;
}
