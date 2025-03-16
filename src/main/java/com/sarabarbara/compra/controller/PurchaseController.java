package com.sarabarbara.compra.controller;

import com.sarabarbara.compra.dto.clients.ClientDTO;
import com.sarabarbara.compra.dto.items.ItemDTO;
import com.sarabarbara.compra.dto.purchases.*;
import com.sarabarbara.compra.model.Purchase;
import com.sarabarbara.compra.responses.SearchResponse;
import com.sarabarbara.compra.responses.purchases.CreatePurchaseResponse;
import com.sarabarbara.compra.responses.purchases.PurchaseSheetResponse;
import com.sarabarbara.compra.responses.purchases.UpdatePurchaseResponse;
import com.sarabarbara.compra.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.sarabarbara.compra.mapper.ClientMapper.toClientDTOMapper;
import static com.sarabarbara.compra.mapper.ItemMapper.toItemDTOMapper;
import static com.sarabarbara.compra.mapper.PurchaseMapper.*;

/**
 * PurchaseController class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@RestController
@AllArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;

    /**
     * The creation purchase controller
     *
     * @param purchase the purchase to create
     *
     * @return the created purchase
     */

    @PostMapping("/create")
    public ResponseEntity<CreatePurchaseResponse> createPurchase(@NotNull @Validated @RequestBody PurchaseCreateDTO purchase) {

        try {

            logger.info("Creating purchase started");

            Purchase createdPurchase = purchaseService.createPurchase(purchase.getIdClient(), purchase.getIdItem(),
                    purchase.getQuantity());

            ClientDTO clientDTO = toClientDTOMapper(createdPurchase.getClient());
            ItemDTO itemDTO = toItemDTOMapper(createdPurchase.getItem());


            PurchaseCreateDTO createPurchaseDTO = toPurchaseCreateDTOMapper(clientDTO.getIdClient(),
                    itemDTO.getIdItem(), createdPurchase.getQuantity());

            logger.info("Creating purchase finished");

            logger.info("Purchase created successfully: {}", createPurchaseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreatePurchaseResponse(true,
                    createPurchaseDTO, "Purchase created successfully"));

        } catch (Exception e) {

            logger.error("Can't create the purchase: Some internal error occurred {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreatePurchaseResponse(false, null,
                            e.getMessage()));
        }

    }

    /**
     * The purchase list controller
     *
     * @param page the page
     * @param size the size of the page
     *
     * @return the list of all purchases
     */

    @GetMapping
    public ResponseEntity<SearchResponse<PurchaseDTO>> purchaseList(@RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {

        try {
            logger.info("List of clients started");

            List<Purchase> purchaseList = purchaseService.purchaseList(page - 1, size);
            int totalPages = (int) Math.ceil((double) purchaseList.size() / size);

            if (purchaseList.isEmpty()) {

                logger.info("Client list finished without content");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<PurchaseDTO> purchaseDTO = toPurchaseDTOListMapper(purchaseList);

            logger.info("List of purchases:");
            purchaseDTO.forEach(purchase -> logger.info("  - client's name: {}, item's name: {}",
                    purchase.getClient().getName(), purchase.getItem().getName()));

            return ResponseEntity.status(HttpStatus.OK).body(new SearchResponse<>(
                    purchaseDTO, purchaseDTO.size(), page, totalPages, "Successful"));

        } catch (Exception e) {

            logger.error("Can't get the purchase list. Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0,
                            e.getMessage()));
        }
    }

    /**
     * The search client controller
     *
     * @param idClient     the id of the client
     * @param idItem       the id of the item
     * @param purchaseDate the date of the purchase
     * @param quantity     the quantity of the purchase
     * @param totalPrice   the total price of the purchase
     * @param page         the page
     * @param size         the size of the page
     *
     * @return the searched client
     */

    @GetMapping("/search")
    public ResponseEntity<SearchResponse<PurchaseSearchDTO>> searchPurchase(
            @RequestParam(required = false) Long idClient,
            @RequestParam(required = false) Long idItem,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate purchaseDate,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) BigDecimal totalPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {

            logger.info("Searching client started");

            List<Purchase> searchedPurchase = purchaseService.searchPurchase(idClient, idItem, purchaseDate, quantity,
                    totalPrice, page, size);

            if (searchedPurchase.isEmpty()) {

                logger.info("No clients found");

                logger.info("Searching purchase finished without content");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<PurchaseSearchDTO> purchaseSearchDTO = toPurchaseSearchDTOMapper(searchedPurchase);

            int totalPage = (int) Math.ceil((double) searchedPurchase.size() / size);

            SearchResponse<PurchaseSearchDTO> response = new SearchResponse<>(
                    purchaseSearchDTO, searchedPurchase.size(), page, totalPage, "Successfully");

            logger.info("List of purchases:");
            purchaseSearchDTO.forEach(purchase -> logger.info("  - idClient: {}, idItem: {}, " +
                            "purchaseDate: {}, quantity: {}, totalPrice: {}", purchase.getClient().getName(),
                    purchase.getItem().getName(), purchase.getPurchaseDate(), purchase.getQuantity(),
                    purchase.getTotalPrice()));

            logger.info("Searching purchase finished");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            logger.error("Can't search client: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0,
                            e.getMessage()));
        }
    }

    /**
     * The  purchase sheet controller
     *
     * @param idPurchase the id of the purchase
     *
     * @return the sheet of the purchase
     */

    @GetMapping("/{idPurchase}")
    public ResponseEntity<PurchaseSheetResponse> clientSheet(@PathVariable Long idPurchase) {

        try {

            logger.info("PurchaseSheet started");

            Purchase purchase = purchaseService.purchaseSheet(idPurchase);

            PurchaseSheet purchaseSheet = toPurchaseSheetMapper(purchase);

            logger.info("Purchase sheet for id {}: {}", idPurchase, purchaseSheet);
            return ResponseEntity.status(HttpStatus.OK).body(new PurchaseSheetResponse(true, purchaseSheet,
                    "Successfully"));

        } catch (Exception e) {

            logger.error("Can't load purchase's sheet: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PurchaseSheetResponse(false, null, e.getMessage()));

        }

    }

    /**
     * The update purchase controller
     *
     * @param idPurchase the id of the purchase
     * @param purchase   the purchase's data
     *
     * @return the updated client
     */

    @PatchMapping("/{idPurchase}/update")
    public ResponseEntity<UpdatePurchaseResponse> updatePurchase(@PathVariable Long idPurchase,
                                                                 @RequestBody Purchase purchase) {

        try {

            logger.info("Updating purchase started");

            Purchase updatedClient = purchaseService.updatePurchase(idPurchase, purchase);

            PurchaseUpdateDTO purchaseUpdateDTO = toPurchaseUpdateDTOMapper(updatedClient);

            logger.info("Purchase updated successfully: {}", purchaseUpdateDTO);

            logger.info("Updating purchase finished");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UpdatePurchaseResponse(true, purchaseUpdateDTO,
                            "Client updated successfully"));

        } catch (Exception e) {

            logger.error("Can't update purchase: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UpdatePurchaseResponse(false, null, e.getMessage()));
        }
    }

    /**
     * The Delete Controller
     *
     * @param idPurchase@return a message
     */

    @DeleteMapping("/{idPurchase}/delete")
    public ResponseEntity<String> deleteClient(@PathVariable Long idPurchase) {

        try {

            logger.info("Deleting purchase started");

            purchaseService.deletePurchase(idPurchase);

            logger.info("Deleting purchase finished");
            return ResponseEntity.status(HttpStatus.OK).body("Purchase deleted successfully");

        } catch (Exception e) {

            logger.error("Can't delete purchase: Some internal error occurred. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
