package com.pichincha.infra.api.router.facade;

import com.pichincha.domain.entities.Client;
import com.pichincha.infra.api.router.controller.dto.response.client.ClientDto;
import com.pichincha.infra.api.router.controller.error.exception.ClientException;

public interface ClientsFacade {

    public ClientDto createClient(Client client) throws ClientException;

    public ClientDto getClientById(Long clientId) throws ClientException;

    public void deleteClient(Long clientId) throws ClientException;

    public ClientDto updateClient(Client client ) throws ClientException;
}
