package com.sarabarbara.compra.dto.items;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarabarbara.compra.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ItemDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/03/2025
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ItemDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_item")
    private Long idItem;

    /**
     * The name;
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The description;
     */

    @NonNull
    @Size(min = 3, max = 500, message = "The description must be between 3 and 500 characters")
    private String description;

    /**
     * The unitPrice;
     */

    @NotNull
    @DecimalMin(value = "0.01", message = "The price must be at least 0.01")
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * The unitStock;
     */

    @NotNull
    @Column(name = "unit_stock")
    private int itemStock;

    /**
     * The type;
     */

    @NonNull
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * The supplier;
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The supplier must be between 3 and 45 characters")
    private String supplier;

    /**
     * The date;
     */

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

}
