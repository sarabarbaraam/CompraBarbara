package com.sarabarbara.compra.dto.clients;

import com.sarabarbara.compra.model.Item;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClientPurchaseDTO class
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
public class ClientPurchaseDTO {

    /**
     * The idItem;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", nullable = false)
    private Item idItem;

    /**
     * The purchaseDate;
     */

    @NonNull
    @Column(name = "purchase_date")
    private Date purchaseDate;

    /**
     * The quantity;
     */

    @NotNull
    private int quantity;

    /**
     * The totalPrice;
     */

    @NonNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;
}
