package com.sarabarbara.compra.service;

import com.sarabarbara.compra.enums.Type;
import com.sarabarbara.compra.exceptions.client.ClientValidateException;
import com.sarabarbara.compra.exceptions.item.ItemNotFoundException;
import com.sarabarbara.compra.exceptions.item.ItemValidateException;
import com.sarabarbara.compra.model.Item;
import com.sarabarbara.compra.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ItemService class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@Service
@AllArgsConstructor
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private ItemRepository itemRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Method to create an item
     *
     * @param item the item's data
     *
     * @return the created item
     *
     * @throws ItemValidateException the {@link ItemValidateException}
     */

    public Item createItem(Item item) throws ItemValidateException {

        logger.info("Creating client...");
        validateNewItem(item);

        logger.info("Client created successfully");
        return itemRepository.save(item);
    }

    /**
     * Method to get the list of all items
     *
     * @param page the page to search
     * @param size the size of the page
     *
     * @return the list of all items
     */

    public List<Item> itemList(int page, int size) {

        logger.info("Client list. Page {}, size {}", page, size);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Item> pageResult = itemRepository.findAll(pageRequest);

        List<Item> itemList = pageResult.getContent();

        logger.info("Clients list: {}. ", itemList);
        return itemList;
    }

    /**
     * Method to search an item
     *
     * @param name      the name of the item
     * @param itemStock the stock of the item
     * @param type      the type of the item
     * @param supplier  the supplier of the item
     * @param date      the date of the purchase or restock
     * @param page      the page
     * @param size      the size of the page
     *
     * @return the item searched
     */

    public List<Item> searchItem(String name, Integer itemStock, Type type, String supplier, LocalDate date, int page,
                                 int size) {

        logger.info("Searching item...");

        if (page < 1) {
            throw new IllegalArgumentException("Page index must be at least 1");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<Item> searchedClient = itemRepository.searchItems(name, itemStock, type, supplier, date, pageRequest);

        logger.info("Clients found: {}", searchedClient.getContent());
        return searchedClient.getContent();

    }

    /**
     * Method to update item's data
     *
     * @param name    the name of the item
     * @param newInfo the new info to be updated
     *
     * @return the updated item
     *
     * @throws ItemNotFoundException the {@link ItemNotFoundException}
     */

    public Item updateItem(String name, Item newInfo) throws ItemNotFoundException {

        logger.info("Updating item with name {}", name);
        Item optionalItem = itemRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("Can't update item: Item not found"));

        logger.info("New item info: {}", newInfo);

        // ignores the id field
        modelMapper.typeMap(Item.class, Item.class)
                .addMappings(mapper -> mapper.skip(Item::setIdItem));

        // ignores the price field
        modelMapper.typeMap(Item.class, Item.class)
                .addMappings(mapper -> mapper.skip(Item::setUnitPrice));

        // ignores the null fields
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        /* copies the values of the Client object (newInfo, any non-null field)
        to the existingClient object. */

        modelMapper.map(newInfo, optionalItem);

        logger.info("Updating item {} ...", optionalItem.getName());
        itemRepository.save(optionalItem);


        logger.info("Item {} updated successfully", optionalItem);
        return optionalItem;
    }

    /**
     * Method to delete an item
     *
     * @param name the name of the item
     *
     * @throws ItemNotFoundException the {@link ItemNotFoundException}
     */

    public void deleteItem(String name) throws ItemNotFoundException {

        Item optionalItem = itemRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("Can't update item: Item not found"));

        Long id = optionalItem.getIdItem();

        logger.info("Deleting item: {}", optionalItem);
        itemRepository.deleteById(id);

        logger.info("Item with id {} (name: {}) has been deleted successfully.", id, name);
    }

    /**
     * Method to see the sheet of the item
     * In this method it searched the id of the item to see their sheet
     *
     * @param idItem the id of the client
     *
     * @return the id of the client
     */

    public Item itemSheet(Long idItem) {

        logger.info("Client sheet for id {}", idItem);

        Optional<Item> item = itemRepository.findById(idItem);

        return item.orElse(null);

    }

    // Complementary methods

    /**
     * Validate the info of new client is correct
     *
     * @param item the client
     */

    private void validateNewItem(@NonNull Item item) {

        logger.info("Validating item...");
        nameValidator(item.getName());
    }

    /**
     * Validates if the name is taken
     *
     * @param name the name
     */

    private void nameValidator(String name) {

        Optional<Item> optionalItem = itemRepository.findByName(name);

        if (optionalItem.isPresent()) {

            logger.error("The name {} is already taken.", name);
            throw new ClientValidateException("The name " + name + " is already taken.");
        }

        logger.info("The name {} is available", name);
    }

}
