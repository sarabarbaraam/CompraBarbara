package com.sarabarbara.compra.responses.item;

import com.sarabarbara.compra.sheets.ItemSheet;
import lombok.*;

/**
 * ItemSheetResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ItemSheetResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The clientSheet
     */

    private ItemSheet itemSheet;

    /**
     * The message
     */

    private String message;

}
