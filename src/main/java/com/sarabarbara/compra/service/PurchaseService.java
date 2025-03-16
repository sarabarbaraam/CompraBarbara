package com.sarabarbara.compra.service;

import com.sarabarbara.compra.exceptions.client.ClientNotFoundException;
import com.sarabarbara.compra.exceptions.item.ItemNotFoundException;
import com.sarabarbara.compra.exceptions.purchase.PurchaseNotFoundException;
import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.model.Item;
import com.sarabarbara.compra.model.Purchase;
import com.sarabarbara.compra.repository.ClientRepository;
import com.sarabarbara.compra.repository.ItemRepository;
import com.sarabarbara.compra.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PurchaseService class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/03/2025
 */

@Service
@AllArgsConstructor
public class PurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    private PurchaseRepository purchaseRepository;
    private ClientRepository clientRepository;
    private ItemRepository itemRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private static final BigDecimal iva = BigDecimal.valueOf(21);

    /**
     * Method to create a purchase
     *
     * @param idCliente the id of the client
     * @param idItem    the id of the item
     * @param quantity  the quantity to buy
     *
     * @return the created purchase
     */

    public Purchase createPurchase(Long idCliente, Long idItem, int quantity) {

        logger.info("Creating purchase...");

        Client optionalClient = clientRepository.findById(idCliente)
                .orElseThrow(() -> new ClientNotFoundException("Can't update client: Client not found"));

        Item optionalItem = itemRepository.findById(idItem)
                .orElseThrow(() -> new ItemNotFoundException("Can't update item: Item not found"));

        BigDecimal total = optionalItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal totalIva = total.multiply(iva).divide(BigDecimal.valueOf(100),
                MathContext.DECIMAL128);
        BigDecimal totalPrice = total.add(totalIva);

        Purchase purchase = Purchase.builder()
                .client(optionalClient)
                .item(optionalItem)
                .purchaseDate(LocalDate.now())
                .quantity(quantity)
                .total(total)
                .iva(iva)
                .totalIva(totalIva)
                .totalPrice(totalPrice)
                .build();

        logger.info("Purchase created successfully");
        return purchaseRepository.save(purchase);
    }

    /**
     * Method to get the list of all purchases
     *
     * @param page the page to search
     * @param size the size of the page
     *
     * @return the list of all purchases
     */

    public List<Purchase> purchaseList(int page, int size) {

        logger.info("Purchase list. Page {}, size {}", page, size);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Purchase> pageResult = purchaseRepository.findAll(pageRequest);

        List<Purchase> purchaseList = pageResult.getContent();

        logger.info("Purchase list: {}. ", purchaseList);
        return purchaseList;
    }

    /**
     * Method to search a purchase
     *
     * @param idClient     the id of the client
     * @param idItem       the id of the item
     * @param purchaseDate the date of the purchase
     * @param quantity     the quantity of the purchase
     * @param totalPrice   the total price of the purchase
     * @param page         the page to search
     * @param size         the size of the page
     *
     * @return the searched client
     */

    public List<Purchase> searchPurchase(Long idClient, Long idItem, LocalDate purchaseDate, Integer quantity,
                                         BigDecimal totalPrice, int page, int size) {

        logger.info("Searching purchase...");

        if (page < 1) {
            throw new IllegalArgumentException("Page index must be at least 1");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<Purchase> searchedPurchase = purchaseRepository.searchPurchase(idClient, idItem, purchaseDate, quantity,
                totalPrice, pageRequest);

        logger.info("Purchases found: {}", searchedPurchase.getContent());
        return searchedPurchase.getContent();

    }

    /**
     * Method to update purchase's data
     *
     * @param idPurchase the id of the purchase
     * @param newInfo    the new info to be updated
     *
     * @return the updated purchase
     *
     * @throws PurchaseNotFoundException the {@link PurchaseNotFoundException}
     */

    public Purchase updatePurchase(Long idPurchase, Purchase newInfo) throws PurchaseNotFoundException {

        logger.info("Updating purchase with id {}", idPurchase);
        Purchase optionalPurchase = purchaseRepository.findByIdPurchase(idPurchase)
                .orElseThrow(() -> new PurchaseNotFoundException("Can't update purchase: Purchase not found"));

        logger.info("New purchase info: {}", newInfo);

        // ignore fields
        modelMapper.typeMap(Purchase.class, Purchase.class).addMappings(mapper -> {

            mapper.skip(Purchase::setIdPurchase);
            mapper.skip(Purchase::setClient);
            mapper.skip(Purchase::setItem);

        });

        // ignores the null fields
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        logger.info("Updating purchase {}...", optionalPurchase);

        if (newInfo.getQuantity() != null && !newInfo.getQuantity().equals(optionalPurchase.getQuantity())) {

            BigDecimal unitPrice = optionalPurchase.getItem().getUnitPrice();
            BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(newInfo.getQuantity()));

            BigDecimal totalIva = total.multiply(iva).divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
            BigDecimal totalPrice = total.add(totalIva);

            optionalPurchase.setTotal(total);
            optionalPurchase.setTotalIva(totalIva);
            optionalPurchase.setTotalPrice(totalPrice);
        }

        /* copies the values of the Purchase object (newInfo, any non-null field)
        to the existingPurchase object. */

        modelMapper.map(newInfo, optionalPurchase);
        purchaseRepository.save(optionalPurchase);

        logger.info("Purchase {} updated successfully", optionalPurchase);
        return optionalPurchase;
    }

    /**
     * Method to delete a purchase
     *
     * @param idPurchase the phone number of the client
     *
     * @throws PurchaseNotFoundException the {@link PurchaseNotFoundException}
     */

    public void deletePurchase(Long idPurchase) throws PurchaseNotFoundException {

        Purchase optionalPurchase = purchaseRepository.findByIdPurchase(idPurchase)
                .orElseThrow(() -> new PurchaseNotFoundException("Can't update purchase: Purchase not found"));

        Long id = optionalPurchase.getIdPurchase();

        logger.info("Deleting purchase: {}", optionalPurchase);
        purchaseRepository.deleteById(id);

        logger.info("Purchase with id {} has been deleted successfully.", id);
    }

    /**
     * Method to see the sheet of the purchase
     * In this method it searched the id of the purchase to see their sheet
     *
     * @param idPurchase the id of the client
     *
     * @return the id of the client
     */

    public Purchase purchaseSheet(Long idPurchase) {

        logger.info("Client sheet for id {}", idPurchase);

        Optional<Purchase> purchase = purchaseRepository.findById(idPurchase);

        return purchase.orElse(null);

    }

}
