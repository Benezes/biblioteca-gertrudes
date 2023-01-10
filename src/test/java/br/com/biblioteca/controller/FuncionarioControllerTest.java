package br.com.biblioteca.controller;

import br.com.biblioteca.controller.utils.AbstractUtilsIT;
import br.com.biblioteca.controller.utils.FakeCPF;
import br.com.biblioteca.dto.request.FuncionarioRequestForm;
import br.com.biblioteca.models.Endereco;
import br.com.biblioteca.models.Funcionario;
import br.com.biblioteca.models.enums.CargoEnum;
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
@DisplayName("FuncionarioController")
class FuncionarioControllerTest extends AbstractUtilsIT {

    private static final String Funcionarios = "http://localhost:8080/funcionarios";


    @Test
    void shouldFindAllFuncionariosPaged() throws Exception {
        ResultActions result = mockMvc.perform(get(Funcionarios).contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void shouldFindAllFuncionarioOrderByIncidencia() throws Exception {

        ResultActions result = mockMvc.perform(get(Funcionarios + "/regiao").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void shouldCreateFuncionario() throws Exception {
        FuncionarioRequestForm dto = createFuncionario();
        dto.setCpf(FakeCPF.geraCPF());
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post(Funcionarios).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateFuncionarioCepInvalido() throws Exception {
        FuncionarioRequestForm dto = createFuncionario();
        dto.setCpf(FakeCPF.geraCPF());
        dto.setCep("99999999912");
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post(Funcionarios).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateFuncionarioWhenIdExists() throws Exception {

        FuncionarioRequestForm dto = createFuncionario();
        dto.setCpf(FakeCPF.geraCPF());
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSalario(dto.getSalario());
        funcionario.setCpf(dto.getCpf());

        Endereco endereco = new Endereco();
        endereco.setCep("05159460");
        endereco.setLogradouro("rua xpto");
        endereco.setNumero("123");
        endereco.setBairro("jd xpto");
        endereco.setLocalidade("São paulo");
        endereco.setUf("sp");
        endereco.setComplemento("");
        funcionario.setEndereco(endereco);
        funcionario = funcionarioRepository.save(funcionario);

        dto.setNome("Gabriel Menezes");
        String jsonBody = objectMapper.writeValueAsString(dto);

        Long existingId = funcionario.getUserId();
        ResultActions result = mockMvc.perform(put(Funcionarios + "/{id}", existingId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(dto.getCpf()));
    }

    @Test
    void shouldDeleteFuncionarioById() throws Exception {

        FuncionarioRequestForm dto = createFuncionario();
        dto.setCpf(FakeCPF.geraCPF());
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSalario(dto.getSalario());
        funcionario.setCpf(dto.getCpf());

        Endereco endereco = new Endereco();
        endereco.setCep("05159460");
        endereco.setLogradouro("rua xpto");
        endereco.setNumero("123");
        endereco.setBairro("jd xpto");
        endereco.setLocalidade("São paulo");
        endereco.setUf("sp");
        funcionario.setEndereco(endereco);
        funcionario = funcionarioRepository.save(funcionario);

        dto.setNome("Gabriel Menezes");
        Long existingId = funcionario.getUserId();

        ResultActions result = mockMvc.perform(delete(Funcionarios + "/{id}", existingId));

        result.andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteFuncionario() throws Exception {

        FuncionarioRequestForm dto = createFuncionario();
        dto.setNome("Gabriel Menezes");
        dto.setCpf(FakeCPF.geraCPF());
        String jsonBody = objectMapper.writeValueAsString(dto);

        Long nonExistingId = 999L;
        ResultActions result = mockMvc.perform(put(Funcionarios + "/{id}", nonExistingId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateFuncionarioWhenIdExistsAndOtherAddress() throws Exception {

        FuncionarioRequestForm dto = createFuncionario();
        dto.setCpf(FakeCPF.geraCPF());
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSalario(dto.getSalario());
        funcionario.setCpf(dto.getCpf());

        Endereco endereco = new Endereco();
        endereco.setCep("11706460");
        endereco.setLogradouro("Rua Doutor Samuel Augusto Leão de Moura");
        endereco.setNumero("123");
        endereco.setBairro("Caiçara");
        endereco.setLocalidade("São paulo");
        endereco.setUf("sp");
        endereco.setComplemento("");
        funcionario.setEndereco(endereco);
        funcionario = funcionarioRepository.save(funcionario);

        dto.setNome("Gabriel Menezes");
        String jsonBody = objectMapper.writeValueAsString(dto);

        Long existingId = funcionario.getUserId();
        ResultActions result = mockMvc.perform(put(Funcionarios + "/{id}", existingId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(dto.getCpf()));
    }

    @Test
    void shouldNotUpdateFuncionario() throws Exception {

        FuncionarioRequestForm dto = createFuncionario();
        dto.setCpf(FakeCPF.geraCPF());
        dto.setNome("Gabriel Menezes");
        String jsonBody = objectMapper.writeValueAsString(dto);

        Long nonExistingId = 999L;
        ResultActions result = mockMvc.perform(put(Funcionarios + "/{id}", nonExistingId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    private FuncionarioRequestForm createFuncionario() {

        FuncionarioRequestForm funcionario = new FuncionarioRequestForm();
        funcionario.setNome("Artorias");
        funcionario.setCep("05159460");
        funcionario.setNumeroResidencia("13");
        funcionario.setComplemento("");
        funcionario.setCargo(CargoEnum.RECEPCIONISTA);
        funcionario.setSalario(6000.0);

        return funcionario;
    }

}