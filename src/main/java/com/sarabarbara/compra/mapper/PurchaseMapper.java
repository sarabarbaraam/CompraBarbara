package com.sarabarbara.compra.mapper;

import com.sarabarbara.compra.dto.purchases.*;
import com.sarabarbara.compra.model.Purchase;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.sarabarbara.compra.mapper.ClientMapper.toClientDTOMapper;
import static com.sarabarbara.compra.mapper.ItemMapper.toItemDTOMapper;

/**
 * PurchaseMapper class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@Builder
public class PurchaseMapper {

    /**
     * The private constructor
     */

    private PurchaseMapper() {

    }

    /**
     * The Purchase to PurchaseCreateDTO Mapper
     *
     * @param idClient the id of the client
     * @param idItem   the id of the item
     * @param quantity the quantity of the purchase
     *
     * @return the PurchaseCreateDTO
     */

    public static PurchaseCreateDTO toPurchaseCreateDTOMapper(Long idClient, Long idItem, Integer quantity) {

        return PurchaseCreateDTO.builder()
                .idClient(idClient)
                .idItem(idItem)
                .quantity(quantity)
                .build();
    }

    /**
     * The Purchase to PurchaseDTO Mapper
     *
     * @param purchaseList the purchase list
     *
     * @return the list of all purchases
     */

    public static List<PurchaseDTO> toPurchaseDTOListMapper(@NotNull List<Purchase> purchaseList) {

        return purchaseList.stream()
                .map(purchase -> PurchaseDTO.builder()
                        .idPurchase(purchase.getIdPurchase())
                        .client(toClientDTOMapper(purchase.getClient()))
                        .item(toItemDTOMapper(purchase.getItem()))
                        .purchaseDate(purchase.getPurchaseDate())
                        .quantity(purchase.getQuantity())
                        .total(purchase.getTotal())
                        .iva(purchase.getIva())
                        .totalIva(purchase.getTotalIva())
                        .totalPrice(purchase.getTotalPrice())
                        .build())
                .toList();
    }

    /**
     * The Purchase to PurchaseSearchDTO Mapper
     *
     * @param purchaseList the purchase list
     *
     * @return the list of the purchase search
     */

    public static List<PurchaseSearchDTO> toPurchaseSearchDTOMapper(@NotNull List<Purchase> purchaseList) {

        return purchaseList.stream()
                .map(purchase -> PurchaseSearchDTO.builder()
                        .client(toClientDTOMapper(purchase.getClient()))
                        .item(toItemDTOMapper(purchase.getItem()))
                        .purchaseDate(purchase.getPurchaseDate())
                        .quantity(purchase.getQuantity())
                        .totalPrice(purchase.getTotalPrice())
                        .build())
                .toList();

    }

    /**
     * The Purchase to PurchaseSheet Mapper
     *
     * @param purchase the purchase
     *
     * @return the purchaseSheet
     */

    public static PurchaseSheet toPurchaseSheetMapper(@NotNull Purchase purchase) {

        return PurchaseSheet.builder()
                .client(toClientDTOMapper(purchase.getClient()))
                .item(toItemDTOMapper(purchase.getItem()))
                .purchaseDate(purchase.getPurchaseDate())
                .quantity(purchase.getQuantity())
                .total(purchase.getTotal())
                .iva(purchase.getIva())
                .totalIva(purchase.getTotalIva())
                .totalPrice(purchase.getTotalPrice())
                .build();
    }

    /**
     * The Purchase to PurchaseUpdateDTO Mapper
     *
     * @param purchase the purchase
     *
     * @return the PurchaseUpdateDTO
     */

    public static PurchaseUpdateDTO toPurchaseUpdateDTOMapper(@NotNull Purchase purchase) {

        return PurchaseUpdateDTO.builder()
                .purchaseDate(purchase.getPurchaseDate())
                .quantity(purchase.getQuantity())
                .total(purchase.getTotal())
                .totalIva(purchase.getTotalIva())
                .totalPrice(purchase.getTotalPrice())
                .build();
    }

}
