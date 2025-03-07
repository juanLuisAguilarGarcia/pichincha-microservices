package com.pichincha.app;

import com.pichincha.ClientsApplication;
import com.pichincha.domain.entities.Client;
import com.pichincha.domain.entities.ContactInformation;
import com.pichincha.domain.entities.Identification;
import com.pichincha.domain.entities.Person;
import com.pichincha.domain.port.db.ClientsPortRepository;
import com.pichincha.infra.api.router.controller.dto.response.client.ClientDto;
import com.pichincha.infra.api.router.controller.error.exception.ClientException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientsApplication.class)
public class ClientsServiceTest {

    @Autowired
    private ClientsService clientsService;

    @MockBean
    private ClientsPortRepository clientsPortRepository;

    @Test
    public void createClientTestSucess() throws ClientException {
        Mockito.when(clientsPortRepository.save(any())).thenReturn(getClient());
        Mockito.when(clientsPortRepository.getClientByIdentificationTypeIdAndIdentificationNumber(any(), any()))
                .thenReturn(Client.builder().build());

        ClientDto response = clientsService.createClient(getClient());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().getClientId(), getClient().getClientId());
    }

    @Test
    public void createClientTestWhenExists() throws ClientException {
        Mockito.when(clientsPortRepository.save(any())).thenReturn(getClient());
        Mockito.when(clientsPortRepository.getClientByIdentificationTypeIdAndIdentificationNumber(any(), any()))
                .thenReturn(getClient());

        Assertions.assertThrows(ClientException.class, () -> clientsService.createClient(getClient()));
    }

    @Test
    public void createClientTestWhenExistsANDIsActiveFalse() throws ClientException {
        Client client = getClient();
        Mockito.when(clientsPortRepository.save(any())).thenReturn(client);

        client.setIsActive(false);
        Mockito.when(clientsPortRepository.getClientByIdentificationTypeIdAndIdentificationNumber(any(), any()))
                .thenReturn(client);

        ClientDto response = clientsService.createClient(getClient());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().getClientId(), getClient().getClientId());
    }

    @Test
    public void createClientTestWhenDataAccessException() throws ClientException {
        Mockito.when(clientsPortRepository.save(any())).thenThrow(new RecoverableDataAccessException("jpa error"));
        Mockito.when(clientsPortRepository.getClientByIdentificationTypeIdAndIdentificationNumber(any(), any()))
                .thenReturn(Client.builder().build());

        Assertions.assertThrows(ClientException.class, () -> clientsService.createClient(getClient()));
    }

    private Client getClient() {
        return Client.builder()
                .clientId(1L)
                .isActive(true)
                .updateAt(Timestamp.valueOf(LocalDateTime.now()))
                .createAt(Timestamp.valueOf(LocalDateTime.now()))
                .password("password")
                .personalInformation(Person.builder()
                        .address("address")
                        .age(13)
                        .firstName("name")
                        .lastName("prueba")
                        .gender("M")
                        .personId(1L)
                        .identification(Identification.builder()
                                .number(1234L)
                                .typeId(1L).build())
                        .contactInformation(ContactInformation.builder()
                                .telephoneNumber(129182L).build()).build()).build();
    }
}
