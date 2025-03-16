package com.sarabarbara.compra.controllers;

import com.sarabarbara.compra.controller.ClientController;
import com.sarabarbara.compra.exceptions.client.ClientValidateException;
import com.sarabarbara.compra.model.Client;
import com.sarabarbara.compra.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ClientControllersTest class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 11/03/2025
 */

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ClientControllersTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    private MockMvc mockMvc;

    private Client client;

    private final LocalDate date = LocalDate.of(1997, 1, 1);

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        client = Client.builder()
                .idClient(1L)
                .name("Name")
                .surname("Surname")
                .company("Company")
                .position("Position")
                .address("Address")
                .zipCode("Zip Code")
                .province("Province")
                .phoneNumber("654567656")
                .birthDate(date)
                .build();
    }

    @Test
    void registerControllerTest() throws Exception {

        String validDate = "01/01/1997";

        when(clientService.createClient(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name\", \"surname\":\"Surname\", \"company\":\"Company\", " +
                                "\"position\":\"Position\", \"address\":\"Address\", \"zipCode\":\"Zip Code\"," +
                                "\"province\":\"Province\", \"phoneNumber\":\"654567656\", " +
                                "\"birthDate\":\"" + validDate + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Client created successfully"))
                .andExpect(jsonPath("$.clientCreate.name").value("Name"))
                .andExpect(jsonPath("$.clientCreate.surname").value("Surname"))
                .andExpect(jsonPath("$.clientCreate.company").value("Company"))
                .andExpect(jsonPath("$.clientCreate.position").value("Position"))
                .andExpect(jsonPath("$.clientCreate.address").value("Address"))
                .andExpect(jsonPath("$.clientCreate.zipCode").value("Zip Code"))
                .andExpect(jsonPath("$.clientCreate.province").value("Province"))
                .andExpect(jsonPath("$.clientCreate.phoneNumber").value("654567656"))
                .andExpect(jsonPath("$.clientCreate.birthDate").value("01/01/1997"));
    }

    @Test
    void registerControllerDuplicatedCompanyTest() throws Exception {

        String validDate = "01/01/1997";

        when(clientService.createClient(any(Client.class)))
                .thenThrow(new ClientValidateException("Can't create client: The company 'Company' is already taken."));

        mockMvc.perform(post("/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name\", \"surname\":\"Surname\", \"company\":\"Company\", " +
                                "\"position\":\"Position\", \"address\":\"Address\", \"zipCode\":\"Zip Code\"," +
                                "\"province\":\"Province\", \"phoneNumber\":\"654567656\", \"birthDate" +
                                "\":\"" + validDate + "\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't create client: The company 'Company' is already taken."));
    }

    @Test
    void registerControllerErrorTest() throws Exception {

        String validDate = "01/01/1997";

        when(clientService.createClient(any(Client.class))).thenThrow(new RuntimeException("Can't create client: Some" +
                " " +
                "internal error occurred."));

        mockMvc.perform(post("/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name\", \"surname\":\"Surname\", \"company\":\"Company\", " +
                                "\"position\":\"Position\", \"address\":\"Address\", \"zipCode\":\"Zip Code\"," +
                                "\"province\":\"Province\", \"phoneNumber\":\"654567656\", \"birthDate" +
                                "\":\"" + validDate + "\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't create client: Some internal error occurred."));
    }

    @Test
    void clientListControllerTest() throws Exception {

        when(clientService.clientList(anyInt(), anyInt())).thenReturn(List.of(client));

        mockMvc.perform(get("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.length()").value(1))
                .andExpect(jsonPath("$.results[0].name").value(client.getName()))
                .andExpect(jsonPath("$.results[0].surname").value(client.getSurname()))
                .andExpect(jsonPath("$.results[0].company").value(client.getCompany()));
    }

    @Test
    void clientListNoContentControllerTest() throws Exception {

        when(clientService.clientList(anyInt(), anyInt())).thenReturn(List.of());

        mockMvc.perform(get("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("654567656")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchClientControllerTest() throws Exception {

        when(clientService.searchClient(
                nullable(String.class), nullable(String.class), nullable(String.class),
                nullable(String.class), nullable(String.class), nullable(String.class),
                nullable(String.class), anyInt(), anyInt()))
                .thenReturn(List.of(client));


        mockMvc.perform(get("/client/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"654567656\"")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name").value("Name"))
                .andExpect(jsonPath("$.results[0].surname").value("Surname"))
                .andExpect(jsonPath("$.results[0].company").value("Company"));
    }

    @Test
    void searchZeroClientControllerTest() throws Exception {

        when(clientService.searchClient(
                nullable(String.class), nullable(String.class), nullable(String.class),
                nullable(String.class), nullable(String.class), nullable(String.class),
                nullable(String.class), anyInt(), anyInt()))
                .thenReturn(List.of());


        mockMvc.perform(get("/client/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("654567656")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchClientErrorControllerTest() throws Exception {

        when(clientService.searchClient(
                nullable(String.class), nullable(String.class), nullable(String.class),
                nullable(String.class), nullable(String.class), nullable(String.class),
                nullable(String.class), anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Can't search client: Some internal error occurred."));

        mockMvc.perform(get("/client/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("654567656")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void clientSheetControllerTest() throws Exception {

        String validDate = "01/01/1997";

        when(clientService.clientSheet(anyLong())).thenReturn(client);

        Long idClient = 1L;
        mockMvc.perform(get("/client/profile/{idClient}", idClient)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name\", \"surname\":\"Surname\", \"company\":\"Company\", " +
                                "\"position\":\"Position\", \"address\":\"Address\", \"zipCode\":\"Zip Code\"," +
                                "\"province\":\"Province\", \"phoneNumber\":\"654567656\", \"birthDate\"" +
                                ":\"" + validDate + "\"}"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Successfully"))
                .andExpect(jsonPath("$.clientSheet.name").value("Name"))
                .andExpect(jsonPath("$.clientSheet.surname").value("Surname"))
                .andExpect(jsonPath("$.clientSheet.company").value("Company"))
                .andExpect(jsonPath("$.clientSheet.position").value("Position"))
                .andExpect(jsonPath("$.clientSheet.address").value("Address"))
                .andExpect(jsonPath("$.clientSheet.zipCode").value("Zip Code"))
                .andExpect(jsonPath("$.clientSheet.province").value("Province"))
                .andExpect(jsonPath("$.clientSheet.phoneNumber").value("654567656"))
                .andExpect(jsonPath("$.clientSheet.birthDate").value(validDate))
                .andExpect(status().isOk());
    }

}
