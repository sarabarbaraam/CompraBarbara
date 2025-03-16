package com.sarabarbara.compra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarabarbara.compra.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Item class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "item")
public class Item implements Serializable {

    /**
     * The serialVersionUID
     */

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The idItem
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_item")
    private Long idItem;

    /**
     * The name
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The description
     */

    @NonNull
    @Size(min = 3, max = 500, message = "The description must be between 3 and 500 characters")
    private String description;

    /**
     * The unitPrice
     */

    @NotNull
    @DecimalMin(value = "0.01", message = "The price must be at least 0.01")
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * The unitStock
     */

    @NotNull
    @Column(name = "unit_stock")
    private int itemStock;

    /**
     * The type
     */

    @NonNull
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * The supplier
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The supplier must be between 3 and 45 characters")
    private String supplier;

    /**
     * The date
     */

    @NonNull
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item item)) return false;
        return getItemStock() == item.getItemStock()
                && Objects.equals(getIdItem(), item.getIdItem())
                && Objects.equals(getName(), item.getName())
                && Objects.equals(getDescription(), item.getDescription())
                && Objects.equals(getUnitPrice(), item.getUnitPrice())
                && getType() == item.getType() && Objects.equals(getSupplier(), item.getSupplier())
                && Objects.equals(getDate(), item.getDate());
    }

    /**
     * The hashcode
     *
     * @return the hash
     */

    @Override
    public int hashCode() {
        return Objects.hash(getIdItem(), getName(), getDescription(), getUnitPrice(), getItemStock(), getType(),
                getSupplier(), getDate());
    }

}
