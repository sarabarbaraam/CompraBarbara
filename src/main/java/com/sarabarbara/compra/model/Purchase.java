package com.sarabarbara.compra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Purchase class
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
@Table(name = "purchase")
public class Purchase implements Serializable {

    /**
     * The serialVersionUID
     */

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The idPurchase;
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_purchase")
    private Long idPurchase;

    /**
     * The idClient;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    /**
     * The idItem;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    /**
     * The purchaseDate;
     */

    @NonNull
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    /**
     * The quantity
     */

    @NotNull
    private Integer quantity;

    /**
     * The total;
     */

    @NonNull
    private BigDecimal total;

    /**
     * The iva
     */

    @NotNull
    private BigDecimal iva;

    /**
     * The totalIva;
     */

    @NonNull
    @Column(name = "total_iva")
    private BigDecimal totalIva;

    /**
     * The totalPrice;
     */

    @NonNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Purchase purchase)) return false;
        return Objects.equals(getQuantity(), purchase.getQuantity())
                && Objects.equals(getIdPurchase(), purchase.getIdPurchase())
                && Objects.equals(getClient(), purchase.getClient())
                && Objects.equals(getItem(), purchase.getItem())
                && Objects.equals(getPurchaseDate(), purchase.getPurchaseDate())
                && Objects.equals(getTotal(), purchase.getTotal())
                && Objects.equals(getIva(), purchase.getIva())
                && Objects.equals(getTotalIva(), purchase.getTotalIva())
                && Objects.equals(getTotalPrice(), purchase.getTotalPrice());
    }

    /**
     * The hashcode
     *
     * @return the hash
     */

    @Override
    public int hashCode() {
        return Objects.hash(getIdPurchase(), getClient(), getItem(), getPurchaseDate(), getQuantity(), getTotal(),
                getIva(), getTotalIva(), getTotalPrice());
    }

    /**
     * The toString
     *
     * @return the string
     */

    @Override
    public String toString() {
        return "Purchase{" +
                "idPurchase=" + idPurchase +
                ", idClient=" + client +
                ", idItem=" + item +
                ", purchaseDate=" + purchaseDate +
                ", quantity=" + quantity +
                ", total=" + total +
                ", iva=" + iva +
                ", totalIva=" + totalIva +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
