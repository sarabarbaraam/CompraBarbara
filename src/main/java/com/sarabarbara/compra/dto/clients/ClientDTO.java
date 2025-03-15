package com.sarabarbara.compra.dto.clients;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * ClientDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/03/2025
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ClientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_client")
    private Long idClient;

    /**
     * The name;
     */

    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The surname;
     */

    @Size(min = 3, max = 45, message = "The surname must be between 3 and 45 characters")
    private String surname;

    /**
     * The company;
     */

    @Size(min = 3, max = 45, message = "The companu must be between 3 and 45 characters")
    private String company;

    /**
     * The position;
     */

    @Size(min = 3, max = 45, message = "The position must be between 3 and 45 characters")
    private String position;

    /**
     * The address;
     */

    @Size(min = 3, max = 45, message = "The address must be between 3 and 45 characters")
    private String address;

    /**
     * The zipCode;
     */

    @Size(min = 5, max = 10, message = "The zipCode must be between 5 and 10 characters")
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * The province;
     */

    @Size(min = 6, max = 45, message = "The province must be between 3 and 45 characters")
    private String province;

    /**
     * The phoneNumber;
     */

    @Size(min = 9, max = 45, message = "The phoneNumber must be between 9 and 45 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The birthDate;
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Past(message = "The birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

}
