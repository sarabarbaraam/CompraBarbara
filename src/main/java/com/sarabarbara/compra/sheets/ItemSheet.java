package com.sarabarbara.compra.sheets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarabarbara.compra.enums.Type;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ItemSheet class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ItemSheet {

    /**
     * The name;
     */

    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The description;
     */

    @Size(min = 3, max = 500, message = "The description must be between 3 and 500 characters")
    private String description;

    /**
     * The unitPrice;
     */

    @DecimalMin(value = "0.01", message = "The price must be at least 0.01")
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * The unitStock;
     */

    @Column(name = "unit_stock")
    private int itemStock;

    /**
     * The type;
     */

    private Type type;

    /**
     * The supplier;
     */

    @Size(min = 3, max = 45, message = "The supplier must be between 3 and 45 characters")
    private String supplier;

    /**
     * The date;
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

}
