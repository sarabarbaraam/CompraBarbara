package com.sarabarbara.compra.responses.purchases;

import com.sarabarbara.compra.dto.purchases.PurchaseSheet;
import lombok.*;

/**
 * PurchaseSheetResponse class
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
public class PurchaseSheetResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The purchaseSheet
     */

    private PurchaseSheet purchaseSheet;

    /**
     * The message
     */

    private String message;

}
