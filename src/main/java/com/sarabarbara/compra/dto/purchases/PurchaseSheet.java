package com.sarabarbara.compra.dto.purchases;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarabarbara.compra.dto.clients.ClientDTO;
import com.sarabarbara.compra.dto.items.ItemDTO;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PurchaseSheet class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PurchaseSheet {

    /**
     * The idClient
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private ClientDTO client;

    /**
     * The idItem
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", nullable = false)
    private ItemDTO item;

    /**
     * The purchaseDate
     */

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    /**
     * The quantity
     */

    @NotNull
    private int quantity;

    /**
     * The total
     */

    @NonNull
    private BigDecimal total;

    /**
     * The iva
     */

    @NonNull
    private BigDecimal iva;

    /**
     * The totalIva
     */

    @NonNull
    @Column(name = "total_iva")
    private BigDecimal totalIva;

    /**
     * The totalPrice
     */

    @NonNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
