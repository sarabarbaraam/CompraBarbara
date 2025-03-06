package com.sarabarbara.compra.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Type class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@AllArgsConstructor
@Getter
@ToString
public enum Type {

    /**
     * The Type enum
     */

    FOOD("Food"),
    BOOKS("Books"),
    HOME("Home"),
    SPORTS("Sports"),
    PETS("Pets");

    /**
     * The description
     */

    private final String description;

}
