package com.sarabarbara.compra.repository;

import com.sarabarbara.compra.enums.Type;
import com.sarabarbara.compra.model.Item;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * ItemRepository class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Searches all the items
     *
     * @param pageable the pageable
     *
     * @return the list of all items
     */

    @NotNull Page<Item> findAll(@NotNull Pageable pageable);

    /**
     * Searches an item
     *
     * @param name      the name of the item
     * @param itemStock the stock of the item
     * @param type      the type of the item
     * @param supplier  the supplier of the item
     * @param date      the date for the purchase or restock
     * @param pageable  the pageable
     *
     * @return the item searched
     */

    // LIKE CONCAT('%', :param, '%') busca coincidencias parciales
    @Query("SELECT i FROM Item i WHERE " +
            "(:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:itemStock IS NULL OR i.itemStock = :itemStock) AND " +
            "(:type IS NULL OR i.type = :type) AND " +
            "(:supplier IS NULL OR LOWER(i.supplier) LIKE LOWER(CONCAT('%', :supplier, '%'))) AND " +
            "(:date IS NULL OR i.date = :date)")
    Page<Item> searchItems(
            @Param("name") String name,
            @Param("itemStock") Integer itemStock,
            @Param("type") Type type,
            @Param("supplier") String supplier,
            @Param("date") LocalDate date,
            @NotNull Pageable pageable
    );



    /**
     * Searches an item by the name
     *
     * @param name the name of the item
     *
     * @return the item searched
     */

    Optional<Item> findByName(@NotNull String name);

    /**
     * Searches an item by the id
     *
     * @param idItem the id of the client
     *
     * @return the item with the id to search
     */

    @NotNull Optional<Item> findById(@NotNull Long idItem);

}