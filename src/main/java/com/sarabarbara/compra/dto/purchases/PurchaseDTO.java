package com.sarabarbara.compra.dto.purchases;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarabarbara.compra.dto.clients.ClientDTO;
import com.sarabarbara.compra.dto.items.ItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PurchaseDTO class
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
public class PurchaseDTO {

    /**
     * The idPurchase;
     */

    private Long idPurchase;

    /**
     * The idClient;
     */

    private ClientDTO client;

    /**
     * The idItem;
     */

    private ItemDTO item;

    /**
     * The purchaseDate;
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate purchaseDate;

    /**
     * The quantity
     */

    private int quantity;

    /**
     * The total;
     */

    private BigDecimal total;

    /**
     * The iva
     */

    private BigDecimal iva;

    /**
     * The totalIva;
     */

    private BigDecimal totalIva;

    /**
     * The totalPrice;
     */

    private BigDecimal totalPrice;

}
