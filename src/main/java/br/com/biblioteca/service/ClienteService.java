package br.com.biblioteca.service;

import br.com.biblioteca.dto.request.ClienteRequestForm;
import br.com.biblioteca.dto.request.EnderecoRequestForm;
import br.com.biblioteca.dto.response.ClienteResponseForm;
import br.com.biblioteca.models.Cliente;
import br.com.biblioteca.repository.ClienteRepository;
import br.com.biblioteca.service.exception.CpfInvalidoFoundException;
import br.com.biblioteca.service.exception.PessoaNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ClienteService {

    public static final String USER_ID_IGNORE_PROPERTIES = "userId";
    public static final String TARGET = "-";
    public static final String REPLACEMENT = "";
    public static final String PESSOA_NAO_ENCONTRADA_EXCEPTION = "Pessoa não encontrada";
    public static final String CPF_JA_EXISTENTE = "Cpf já existente";

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ExternalCall externalApiCall;

    public Page<ClienteResponseForm> findAllClientesPaged(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(ClienteResponseForm::new);
    }

    @Transactional
    public ClienteResponseForm createCliente(ClienteRequestForm cliente) {
        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new CpfInvalidoFoundException(CPF_JA_EXISTENTE);
        }
        EnderecoRequestForm endereco = externalApiCall.createEndereco(cliente.getCep(), cliente.getNumeroResidencia());
        cliente.setEndereco(endereco);
        Cliente clienteEntity = new Cliente(cliente);
        clienteEntity.setDataCriacao(LocalDateTime.now());
        return new ClienteResponseForm(clienteRepository.save(clienteEntity));
    }

    @Transactional
    public ClienteResponseForm updateCliente(Long id, ClienteRequestForm cliente) {
        Cliente clienteEntity = clienteRepository.findById(id).orElseThrow(() -> new PessoaNotFoundException(PESSOA_NAO_ENCONTRADA_EXCEPTION));
        if (!cliente.getCep().equals(clienteEntity.getEndereco().getCep().replace(TARGET, REPLACEMENT))) {
            cliente.setEndereco(
                    externalApiCall.createEndereco(cliente.getCep(), cliente.getNumeroResidencia()));
        }
        BeanUtils.copyProperties(cliente, clienteEntity, USER_ID_IGNORE_PROPERTIES);
        clienteEntity.setDataUltimaAtualizacao(LocalDateTime.now());

        if (!clienteEntity.getEndereco().getComplemento().equals(cliente.getComplemento()))
            clienteEntity.getEndereco().setComplemento(cliente.getComplemento());

        return new ClienteResponseForm(clienteRepository.save(clienteEntity));
    }
}
