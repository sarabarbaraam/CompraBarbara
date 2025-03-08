package com.sarabarbara.compra.repository;


import com.sarabarbara.compra.model.Client;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ClientRepository class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @NotNull Page<Client> findAll(@NotNull Pageable pageable);

    // LIKE CONCAT('%', :param, '%') busca coincidencias parciales
    @Query("SELECT c FROM Client c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:surname IS NULL OR LOWER(c.surname) LIKE LOWER(CONCAT('%', :surname, '%'))) AND " +
            "(:company IS NULL OR LOWER(c.company) LIKE LOWER(CONCAT('%', :company, '%'))) AND " +
            "(:position IS NULL OR LOWER(c.position) LIKE LOWER(CONCAT('%', :position, '%'))) AND " +
            "(:zipCode IS NULL OR LOWER(c.zipCode) LIKE LOWER(CONCAT('%', :zipCode, '%'))) AND " +
            "(:province IS NULL OR LOWER(c.province) LIKE LOWER(CONCAT('%', :province, '%'))) AND " +
            "(:phoneNumber IS NULL OR LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :phoneNumber, '%')))")
    Page<Client> searchClients(
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("company") String company,
            @Param("position") String position,
            @Param("zipCode") String zipCode,
            @Param("province") String province,
            @Param("phoneNumber") String phoneNumber,
            @NotNull Pageable pageable
    );

}
