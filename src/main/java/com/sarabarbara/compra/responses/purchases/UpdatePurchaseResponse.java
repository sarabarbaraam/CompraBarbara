package com.sarabarbara.compra.responses.purchases;

import com.sarabarbara.compra.dto.purchases.PurchaseUpdateDTO;
import lombok.*;

/**
 * UpdatePurchaseResponse class
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
public class UpdatePurchaseResponse {
    /**
     * The success
     */

    private boolean success;

    /**
     * The purchaseUpdate
     */

    private PurchaseUpdateDTO purchaseUpdate;

    /**
     * The message
     */

    private String message;

}
