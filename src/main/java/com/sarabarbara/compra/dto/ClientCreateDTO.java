package com.sarabarbara.compra.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * ClientCreateDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/03/2025
 */

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientCreateDTO {

    /**
     * The name;
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The surname;
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The surname must be between 3 and 45 characters")
    private String surname;

    /**
     * The company;
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The companu must be between 3 and 45 characters")
    private String company;

    /**
     * The position;
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The position must be between 3 and 45 characters")
    private String position;

    /**
     * The address;
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The address must be between 3 and 45 characters")
    private String address;

    /**
     * The zipCode;
     */

    @NonNull
    @Size(min = 5, max = 10, message = "The zipCode must be between 5 and 10 characters")
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * The province;
     */

    @NonNull
    @Size(min = 6, max = 45, message = "The province must be between 3 and 45 characters")
    private String province;

    /**
     * The phoneNumber;
     */

    @NonNull
    @Size(min = 9, max = 45, message = "The phoneNumber must be between 9 and 45 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The birthDate;
     */

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Past(message = "The birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

}
