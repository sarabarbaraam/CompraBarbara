package com.sarabarbara.compra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
    private Client idClient;

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
     * The total;
     */

    @NonNull
    private BigDecimal total;

    /**
     * The iva;
     */

    @NonNull
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
        return getQuantity() == purchase.getQuantity()
                && Objects.equals(getIdPurchase(), purchase.getIdPurchase())
                && Objects.equals(getIdClient(), purchase.getIdClient())
                && Objects.equals(getIdItem(), purchase.getIdItem())
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
        return Objects.hash(getIdPurchase(), getIdClient(), getIdItem(), getPurchaseDate(), getQuantity(), getTotal(),
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
                ", idClient=" + idClient +
                ", idItem=" + idItem +
                ", purchaseDate=" + purchaseDate +
                ", quantity=" + quantity +
                ", total=" + total +
                ", iva=" + iva +
                ", totalIva=" + totalIva +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
