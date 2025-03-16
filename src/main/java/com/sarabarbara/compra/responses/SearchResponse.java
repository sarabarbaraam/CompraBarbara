package com.sarabarbara.compra.responses;

import lombok.*;

import java.util.List;

/**
 * SearchResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 07/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchResponse<T> {

    /**
     * The results
     */

    private List<T> results;

    /**
     * The totalResults
     */

    private int totalResults;

    /**
     * The currentPage
     */

    private int currentPage;

    /**
     * The totalPage
     */

    private int totalPage;

    /**
     * The message
     */

    private String message;

}
