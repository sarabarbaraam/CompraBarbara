package com.sarabarbara.compra.mapper;


import com.sarabarbara.compra.dto.items.ItemCreateDTO;
import com.sarabarbara.compra.dto.items.ItemDTO;
import com.sarabarbara.compra.dto.items.ItemSearchDTO;
import com.sarabarbara.compra.dto.items.ItemUpdateDTO;
import com.sarabarbara.compra.enums.Type;
import com.sarabarbara.compra.model.Item;
import com.sarabarbara.compra.sheets.ItemSheet;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * toItemCreateDTOMapper class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@Builder
public class ItemMapper {

    /**
     * The private constructor
     */

    private ItemMapper() {

    }

    public static ItemDTO toItemDTOMapper(@NotNull Item item) {

        return ItemDTO.builder()
                .idItem(item.getIdItem())
                .name(item.getName())
                .description(item.getDescription())
                .unitPrice(item.getUnitPrice())
                .itemStock(item.getItemStock())
                .type(item.getType())
                .supplier(item.getSupplier())
                .date(item.getDate())
                .build();
    }

    /**
     * The Item to ItemCreateDTO mapper
     *
     * @param name        the name of the item
     * @param description the description of the item
     * @param unitPrice   the price of one unit of the item
     * @param itemStock   the stock of the item
     * @param type        the type of the item
     * @param supplier    the supplier of the item
     * @param date        the date of the purchase or restock
     *
     * @return the ItemCreateDTO
     */

    public static ItemCreateDTO toItemCreateDTOMapper(String name, String description, BigDecimal unitPrice,
                                                      int itemStock, Type type, String supplier,
                                                      LocalDate date) {

        return ItemCreateDTO.builder()
                .name(name)
                .description(description)
                .unitPrice(unitPrice)
                .itemStock(itemStock)
                .type(type)
                .supplier(supplier)
                .date(date)
                .build();
    }

    /**
     * The Item to ItemDTO Mapper
     *
     * @param itemList the client list
     *
     * @return the ItemDTO
     */

    public static List<ItemDTO> toItemDTOListMapper(@NotNull List<Item> itemList) {

        return itemList.stream()
                .map(item -> ItemDTO.builder()
                        .idItem(item.getIdItem())
                        .name(item.getName())
                        .description(item.getDescription())
                        .unitPrice(item.getUnitPrice())
                        .itemStock(item.getItemStock())
                        .type(item.getType())
                        .supplier(item.getSupplier())
                        .date(item.getDate())
                        .build())
                .toList();
    }

    /**
     * The Item to ItemSearchDTOMapper
     *
     * @param searchedItem the searched item
     *
     * @return the searched item
     */

    public static List<ItemSearchDTO> toItemSearchDTOMapper(@NotNull List<Item> searchedItem) {

        return searchedItem.stream()
                .map(item -> ItemSearchDTO.builder()
                        .name(item.getName())
                        .itemStock(item.getItemStock())
                        .type(item.getType())
                        .supplier(item.getSupplier())
                        .date(item.getDate())
                        .build())
                .toList();
    }

    /**
     * The Item to ItemSheetDTOMapper
     *
     * @param item the item
     *
     * @return the item sheet
     */

    public static ItemSheet toItemSheetDTOMapper(@NotNull Item item) {

        return ItemSheet.builder()
                .name(item.getName())
                .description(item.getDescription())
                .unitPrice(item.getUnitPrice())
                .itemStock(item.getItemStock())
                .type(item.getType())
                .supplier(item.getSupplier())
                .date(item.getDate())
                .build();
    }

    /**
     * The Item to ItemUpdateDTO Mapper
     *
     * @param item the item
     *
     * @return the ItemUpdateDTO
     */

    public static ItemUpdateDTO toItemUpdateDTOMapper(@NotNull Item item) {

        return ItemUpdateDTO.builder()
                .name(item.getName())
                .description(item.getDescription())
                .unitPrice(item.getUnitPrice())
                .itemStock(item.getItemStock())
                .type(item.getType())
                .supplier(item.getSupplier())
                .date(item.getDate())
                .build();
    }

}
