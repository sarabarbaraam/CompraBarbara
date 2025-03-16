package com.sarabarbara.compra.dto.purchases;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PurchaseUpdateDTO class
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
public class PurchaseUpdateDTO {

    /**
     * The purchaseDate
     */

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    /**
     * The quantity
     */

    private int quantity;

    /**
     * The total
     */

    private BigDecimal total;

    /**
     * The iva
     */

    private BigDecimal iva;

    /**
     * The totalIva
     */

    @Column(name = "total_iva")
    private BigDecimal totalIva;

    /**
     * The totalPrice
     */

    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
