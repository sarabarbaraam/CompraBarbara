package com.sarabarbara.compra.dto.purchases;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarabarbara.compra.dto.clients.ClientDTO;
import com.sarabarbara.compra.dto.items.ItemDTO;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PurchaseSearchDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@Builder
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PurchaseSearchDTO {

    /**
     * The client;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private ClientDTO client;

    /**
     * The item;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", nullable = false)
    private ItemDTO item;

    /**
     * The purchaseDate;
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    /**
     * The quantity
     */

    private int quantity;

    /**
     * The totalPrice;
     */

    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
