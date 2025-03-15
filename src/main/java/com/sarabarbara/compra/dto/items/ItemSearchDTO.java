package com.sarabarbara.compra.dto.items;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarabarbara.compra.enums.Type;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

/**
 * ItemSearchDTO class
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
public class ItemSearchDTO {

    /**
     * The name;
     */

    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The unitStock;
     */

    @Column(name = "unit_stock")
    private int itemStock;

    /**
     * The type;
     */

    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * The supplier;
     */

    @Size(min = 3, max = 45, message = "The supplier must be between 3 and 45 characters")
    private String supplier;

    /**
     * The date;
     */

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

}
