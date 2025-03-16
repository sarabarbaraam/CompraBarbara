package com.sarabarbara.compra.responses.clients;

import com.sarabarbara.compra.sheets.ClientSheet;
import lombok.*;

/**
 * ClientSheetResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 11/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientSheetResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The clientSheet
     */

    private ClientSheet clientSheet;

    /**
     * The message
     */

    private String message;

}
