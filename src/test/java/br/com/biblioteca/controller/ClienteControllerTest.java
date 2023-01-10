package br.com.biblioteca.controller;

import br.com.biblioteca.controller.utils.AbstractUtilsIT;
import br.com.biblioteca.controller.utils.FakeCPF;
import br.com.biblioteca.dto.request.ClienteRequestForm;
import br.com.biblioteca.models.Cliente;
import br.com.biblioteca.models.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("ClienteController")
class ClienteControllerTest extends AbstractUtilsIT {

    private static final String CLIENTES = "http://localhost:8080/clientes";


    @Test
    void shouldFindAllClientesPaged() throws Exception {
        ResultActions result = mockMvc.perform(get(CLIENTES).contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void shouldCreateCliente() throws Exception {
        ClienteRequestForm dto = createCliente();
        dto.setCpf(FakeCPF.geraCPF());
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post(CLIENTES).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateClienteCepInvalido() throws Exception {
        ClienteRequestForm dto = createCliente();
        dto.setCep("99999999912");
        dto.setCpf(FakeCPF.geraCPF());
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post(CLIENTES).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateClienteWhenIdExists() throws Exception {

        ClienteRequestForm dto = createCliente();
        dto.setCpf(FakeCPF.geraCPF());
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setNumeroCelular(dto.getNumeroCelular());

        Endereco endereco = new Endereco();
        endereco.setCep("05159460");
        endereco.setLogradouro("rua xpto");
        endereco.setNumero("123");
        endereco.setBairro("jd xpto");
        endereco.setLocalidade("São paulo");
        endereco.setUf("sp");
        endereco.setComplemento("");
        cliente.setEndereco(endereco);
        cliente = clienteRepository.save(cliente);

        dto.setNome("Gabriel Menezes");
        String jsonBody = objectMapper.writeValueAsString(dto);

        Long existingId = cliente.getUserId();
        ResultActions result = mockMvc.perform(put(CLIENTES + "/{id}", existingId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(dto.getCpf()));
    }

    @Test
    void shouldUpdateClienteWhenIdExistsAndOtherAddress() throws Exception {

        ClienteRequestForm dto = createCliente();
        dto.setCpf(FakeCPF.geraCPF());
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setNumeroCelular(dto.getNumeroCelular());

        Endereco endereco = new Endereco();
        endereco.setCep("11706460");
        endereco.setLogradouro("Rua Doutor Samuel Augusto Leão de Moura");
        endereco.setNumero("123");
        endereco.setBairro("Caiçara");
        endereco.setLocalidade("São paulo");
        endereco.setUf("sp");
        endereco.setComplemento("");
        cliente.setEndereco(endereco);
        cliente = clienteRepository.save(cliente);

        dto.setNome("Gabriel Menezes");
        String jsonBody = objectMapper.writeValueAsString(dto);

        Long existingId = cliente.getUserId();
        ResultActions result = mockMvc.perform(put(CLIENTES + "/{id}", existingId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(dto.getCpf()));
    }

    @Test
    void shouldNotUpdateCliente() throws Exception {

        ClienteRequestForm dto = createCliente();
        dto.setNome("Gabriel Menezes");
        dto.setCpf(FakeCPF.geraCPF());
        String jsonBody = objectMapper.writeValueAsString(dto);

        Long nonExistingId = 999L;
        ResultActions result = mockMvc.perform(put(CLIENTES + "/{id}", nonExistingId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    private ClienteRequestForm createCliente() {

        ClienteRequestForm cliente = new ClienteRequestForm();
        cliente.setNome("Artorias");
        cliente.setCep("11706460");
        cliente.setNumeroResidencia("10");
        cliente.setComplemento("");
        cliente.setNumeroCelular("11911111111");
        cliente.setEmail("xxxxyyyyzzz@hotmail.com");

        return cliente;
    }

}