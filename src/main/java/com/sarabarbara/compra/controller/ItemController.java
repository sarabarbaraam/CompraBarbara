package com.sarabarbara.compra.controller;

import com.sarabarbara.compra.dto.items.ItemCreateDTO;
import com.sarabarbara.compra.dto.items.ItemDTO;
import com.sarabarbara.compra.dto.items.ItemSearchDTO;
import com.sarabarbara.compra.dto.items.ItemUpdateDTO;
import com.sarabarbara.compra.enums.Type;
import com.sarabarbara.compra.exceptions.item.ItemValidateException;
import com.sarabarbara.compra.model.Item;
import com.sarabarbara.compra.responses.SearchResponse;
import com.sarabarbara.compra.responses.item.CreateItemResponse;
import com.sarabarbara.compra.responses.item.ItemSheetResponse;
import com.sarabarbara.compra.responses.item.UpdateItemResponse;
import com.sarabarbara.compra.service.ItemService;
import com.sarabarbara.compra.sheets.ItemSheet;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.sarabarbara.compra.mapper.ItemMapper.*;

/**
 * ItemController class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@RestController
@AllArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    /**
     * The creation item controller
     *
     * @param item the item to create
     *
     * @return the created item
     */

    @PostMapping("/create")
    public ResponseEntity<CreateItemResponse> createItem(@NotNull @Validated @RequestBody Item item) {

        try {

            logger.info("Creating item started");

            Item createdItem = itemService.createItem(item);

            ItemCreateDTO createItemDTO = toItemCreateDTOMapper(createdItem.getName(),
                    createdItem.getDescription(), createdItem.getUnitPrice(), createdItem.getItemStock(),
                    createdItem.getType(), createdItem.getSupplier(), createdItem.getDate());

            logger.info("Item finished");

            logger.info("Item created successfully: {}", createItemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateItemResponse(true, createItemDTO,
                    "Item created successfully"));
        } catch (ItemValidateException iv) {

            logger.error("Can't create the item: A conflict had occurred {}", iv.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CreateItemResponse(false, null, iv.getMessage()));

        } catch (Exception e) {

            logger.error("Can't create the item: Some internal error occurred {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateItemResponse(false, null, e.getMessage()));
        }

    }

    /**
     * The item list controller
     *
     * @param page the page
     * @param size the size of the page
     *
     * @return the list of all items
     */

    @GetMapping
    public ResponseEntity<SearchResponse<ItemDTO>> itemList(@RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "10") int size) {

        try {
            logger.info("List of items started");

            List<Item> itemList = itemService.itemList(page - 1, size);
            int totalPages = (int) Math.ceil((double) itemList.size() / size);

            if (itemList.isEmpty()) {

                logger.info("Items list finished without content");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<ItemDTO> itemDTO = toItemDTOListMapper(itemList);

            logger.info("List of items:");
            itemDTO.forEach(item -> logger.info("  - name: {}, type: {}, stock: {}",
                    item.getName(), item.getType(), item.getItemStock()));

            return ResponseEntity.status(HttpStatus.OK).body(new SearchResponse<>(
                    itemDTO, itemDTO.size(), page, totalPages, "Successful"));

        } catch (Exception e) {

            logger.error("Can't get the item list. Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0,
                            e.getMessage()));
        }
    }

    /**
     * The search item controller
     *
     * @param page the page
     * @param size the size of the page
     *
     * @return the searched item
     */

    @PostMapping("/search")
    public ResponseEntity<SearchResponse<ItemSearchDTO>> searchItem(@RequestParam(required = false) String name,
                                                                    @RequestParam(required = false) Integer itemStock,
                                                                    @RequestParam(required = false) Type type,
                                                                    @RequestParam(required = false) String supplier,
                                                                    @RequestParam(required = false) LocalDate date,
                                                                    @RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {

        try {

            logger.info("Searching item started");

            List<Item> searchedItem = itemService.searchItem(name, itemStock, type, supplier, date, page, size);

            if (searchedItem.isEmpty()) {

                logger.info("No item found");

                logger.info("Searching item finished without content");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<ItemSearchDTO> itemSearchDTO = toItemSearchDTOMapper(searchedItem);

            int totalPage = (int) Math.ceil((double) searchedItem.size() / size);

            SearchResponse<ItemSearchDTO> response = new SearchResponse<>(
                    itemSearchDTO, searchedItem.size(), page, totalPage, "Successfully");

            logger.info("List of items:");
            itemSearchDTO.forEach(itemSearched -> logger.info("  - name: {}, type: {}, stock: {}",
                    itemSearched.getName(), itemSearched.getType(), itemSearched.getItemStock()));

            logger.info("Searching item finished");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            logger.error("Can't search item: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0,
                            e.getMessage()));
        }
    }

    /**
     * The  item sheet controller
     *
     * @param idItem the id of the item
     *
     * @return the sheet of the item
     */

    @GetMapping("/{idItem}")
    public ResponseEntity<ItemSheetResponse> itemSheet(@PathVariable Long idItem) {

        try {

            logger.info("ItemSheet started");

            Item item = itemService.itemSheet(idItem);

            ItemSheet itemSheet = toItemSheetDTOMapper(item);

            logger.info("Item sheet for id {}: {}", idItem, itemSheet);
            return ResponseEntity.status(HttpStatus.OK).body(new ItemSheetResponse(true, itemSheet,
                    "Successfully"));

        } catch (Exception e) {

            logger.error("Can't load item's sheet: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ItemSheetResponse(false, null, e.getMessage()));

        }

    }

    /**
     * The update item controller
     *
     * @param name the name of the item to search for
     * @param item the item's data
     *
     * @return the updated item
     */

    @PatchMapping("/{name}/update")
    public ResponseEntity<UpdateItemResponse> updateItem(@PathVariable String name,
                                                         @RequestBody Item item) {

        try {

            logger.info("Updating item started");

            Item updatedItem = itemService.updateItem(name, item);

            ItemUpdateDTO itemUpdateDTO = toItemUpdateDTOMapper(updatedItem);

            logger.info("Item updated successfully: {}", itemUpdateDTO);

            logger.info("Updating item finished");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UpdateItemResponse(true, itemUpdateDTO, "Item updated successfully"));

        } catch (Exception e) {

            logger.error("Can't update item: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UpdateItemResponse(false, null, e.getMessage()));
        }
    }

    /**
     * The Delete Controller
     *
     * @param name the name of the item to delete
     *
     * @return a message
     */

    @DeleteMapping("/{name}/delete")
    public ResponseEntity<String> deleteItem(@PathVariable String name) {

        try {

            logger.info("Deleting item started");

            itemService.deleteItem(name);

            logger.info("Deleting item finished");
            return ResponseEntity.status(HttpStatus.OK).body("Item deleted successfully");

        } catch (Exception e) {

            logger.error("Can't delete item: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
