package com.sarabarbara.compra.repository;


import com.sarabarbara.compra.model.Purchase;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * PurchaseRepository class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    /**
     * Searches all the purchases
     *
     * @param pageable the pageable
     *
     * @return the list of all purchases
     */

    @NotNull Page<Purchase> findAll(@NotNull Pageable pageable);

    /**
     * Searches a purchase by any of this param
     *
     * @param idClient     the id of the client
     * @param idItem       the id of the item
     * @param purchaseDate the date of the purchase
     * @param quantity     the quantity of the purchase
     * @param totalPrice   the total price
     * @param pageable     the pageable
     *
     * @return the purchase to search
     */

    @Query("SELECT p FROM Purchase p WHERE " +
            "(:idClient IS NULL OR p.client.id = :idClient) AND " +
            "(:idItem IS NULL OR p.item.id = :idItem) AND " +
            "(:purchaseDate IS NULL OR p.purchaseDate = :purchaseDate) AND " +
            "(:quantity IS NULL OR p.quantity = :quantity) AND " +
            "(:totalPrice IS NULL OR p.totalPrice = :totalPrice)")
    Page<Purchase> searchPurchase(
            @Param("idClient") Long idClient,   // Ahora es Long, no Client
            @Param("idItem") Long idItem,       // Ahora es Long, no Item
            @Param("purchaseDate") LocalDate purchaseDate,
            @Param("quantity") Integer quantity,
            @Param("totalPrice") BigDecimal totalPrice,
            Pageable pageable
    );



    /**
     * Find a purchase by the id
     *
     * @param idPurchase the id of the purchase
     *
     * @return the purchase to search
     */

    Optional<Purchase> findByIdPurchase(Long idPurchase);

}
