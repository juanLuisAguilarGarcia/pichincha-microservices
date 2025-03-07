package com.pichincha.infra.api.router.controller;

import com.pichincha.ClientsApplication;
import com.pichincha.app.ClientsService;
import com.pichincha.infra.adapter.db.ClientsRepository;
import com.pichincha.infra.api.router.controller.dto.response.client.ClientDataDto;
import com.pichincha.infra.api.router.controller.dto.response.client.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ClientsApplication.class })
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ClientsControllerIntegrationsTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ClientsService clientsService;

    @MockBean
    private ClientsRepository clientsRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {

        Mockito.when(clientsService.getClientById(any())).thenReturn(getClientDto());


         this.mockMvc.perform(get("/client/{client_id}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

    }

    ClientDto getClientDto(){
        ClientDto response = ClientDto.builder().data(ClientDataDto.builder().build()).build();
        response.setCode("200");
        response.setMessage("ok");
        return response;
    }
}
